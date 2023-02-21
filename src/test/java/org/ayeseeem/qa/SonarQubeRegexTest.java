package org.ayeseeem.qa;

import static org.ayeseeem.qa.SonarQubeRegexTest.RegexMatcher.matches;
import static org.ayeseeem.qa.SonarQubeRegexTest.RegexMatcher.misses;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

/**
 * Checks some of the SonarQube rules.
 */
public class SonarQubeRegexTest {

    @Test
    public void testConstantNaming_AllowDoubleUnderscores() {
        Pattern theirs = Pattern.compile("^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$");
        Pattern ours = Pattern.compile("^[A-Z][A-Z0-9]*(_{1,2}[A-Z0-9]+)*$");

        String[] allowed = new String[] { "A", "A1", "ABC", "A1C", "ABC_DEF", "ABC_DEF_GHI" };
        String[] rejected = new String[] { "", "1", "123", "1ABC", "123_456", "TRIPLE___UNDERSCORE", "lower_case" };

        assertThat(theirs, matches(allowed));
        assertThat(ours, matches(allowed));
        assertThat(theirs, misses(rejected));
        assertThat(ours, misses(rejected));

        String[] exceptions = new String[] { "A__TYPE", "A_THING_WITH__TYPE", "A_THING_WITH__COMPLEX_TYPE" };

        assertThat(theirs, misses(exceptions));
        assertThat(ours, matches(exceptions));
    }

    @Test
    public void testTypeNaming_ChangesFromSonarQubeDefault() {
        String sqDefault = "^[A-Z][0-9]?$";
        Pattern theirs = Pattern.compile(sqDefault);
        // Normal SonarQube rules, or one or more capitalized words, followed by T:
        Pattern ours = Pattern.compile("(" + sqDefault + ")|(^(([A-Z][a-z0-9]+)+T)$)");

        String[] allowed = new String[] { "A", "B", "T", "A1", "A2" };
        String[] rejected = new String[] { "", "1", "123", "1A", "A_1", "ABC", "ABC_DEF", "AB", "TT",
                "EntityThing", "Entity3Thing", "3ishT", "EntityTA", "EntityTT", "EntityTAT" };

        assertThat(theirs, matches(allowed));
        assertThat(ours, matches(allowed));
        assertThat(theirs, misses(rejected));
        assertThat(ours, misses(rejected));

        String[] exceptions = new String[] { "EntityT", "ComplicatedEntityT", "Entity3T", "E3T", "E123T", "ToT" };

        assertThat(theirs, misses(exceptions));
        assertThat(ours, matches(exceptions));
    }

    static class RegexMatcher extends TypeSafeDiagnosingMatcher<Pattern> {

        private final boolean shouldMatch;
        private final String expectedCondition;
        private final String failureCondition;

        private final List<String> cases;

        static RegexMatcher matches(String... cases) {
            return new RegexMatcher(true, cases);
        }

        static RegexMatcher misses(String... cases) {
            return new RegexMatcher(false, cases);
        }

        public RegexMatcher(boolean shouldMatch, String... cases) {
            this.shouldMatch = shouldMatch;
            this.expectedCondition = shouldMatch ? "match" : "miss";
            this.failureCondition = shouldMatch ? "miss" : "match";
            this.cases = Arrays.asList(cases);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("All cases to " + expectedCondition);
        }

        @Override
        protected boolean matchesSafely(Pattern pattern, Description mismatchDescription) {
            List<String> failures = checkAll(pattern);

            if (failures.isEmpty()) {
                return true;
            }

            mismatchDescription.appendText("these " + failureCondition + ": ");
            mismatchDescription.appendValue(failures);

            return false;
        }

        private List<String> checkAll(Pattern pattern) {
            List<String> problems = new ArrayList<>();

            for (String testCase : cases) {
                if (pattern.matcher(testCase).matches() != shouldMatch) {
                    problems.add(testCase);
                }
            }

            return problems;
        }
    }

}
