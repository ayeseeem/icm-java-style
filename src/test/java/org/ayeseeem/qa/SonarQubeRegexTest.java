package org.ayeseeem.qa;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.regex.Pattern;

import org.junit.Test;

public class SonarQubeRegexTest {

    @Test
    public void testConstantNaming_AllowDoubleUnderscores() {
        ReChecker theirs = new ReChecker("^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$");
        ReChecker ours = new ReChecker("^[A-Z][A-Z0-9]*(_{1,2}[A-Z0-9]+)*$");

        String[] allowed = new String[] { "A", "A1", "ABC", "A1C", "ABC_DEF", "ABC_DEF_GHI" };
        String[] rejected = new String[] { "", "1", "123", "1ABC", "123_456", "TRIPLE___UNDERSCORE" };

        theirs.allows(allowed).rejects(rejected);
        ours.allows(allowed).rejects(rejected);

        String[] exceptions = new String[] { "DOUBLE__UNDERSCORE", "A__TYPE", "A_THING_WITH__TYPE", "A_THING_WITH__COMPLEX_TYPE" };

        theirs.rejects(exceptions);
        ours.allows(exceptions);
    }

    @Test
    public void testTypeNaming_ChangesFromSonarQubeDefault() {
        String sqDefault = "^[A-Z][0-9]?$";
        ReChecker theirs = new ReChecker(sqDefault);
        // Normal SonarQube rules, or one or more capitalized words, followed by T:
        ReChecker ours = new ReChecker("(" + sqDefault + ")|(^(([A-Z][a-z0-9]+)+T)$)");

        String[] allowed = new String[] { "A", "B", "T", "A1", "A2" };
        String[] rejected = new String[] { "", "1", "123", "1A", "A_1", "ABC", "ABC_DEF", "AB", "TT",
                "EntityThing", "Entity3Thing", "3ishT", "EntityTA", "EntityTT", "EntityTAT" };

        theirs.allows(allowed).rejects(rejected);
        ours.allows(allowed).rejects(rejected);

        String[] exceptions = new String[] { "EntityT", "ComplicatedEntityT", "Entity3T", "E3T", "E123T", "ToT" };

        theirs.rejects(exceptions);
        ours.allows(exceptions);
    }

    /**
     * Regular Expression checker.
     */
    private static class ReChecker extends Checker<String> {

        private final Pattern pattern;

        ReChecker(String regex) {
            this.pattern = Pattern.compile(regex);
        }

        @Override
        protected boolean isAllowed(String example) {
            return pattern.matcher(example).matches();
        }
    }

    /**
     * Checker to check whether examples are allowed or rejected.
     *
     * @param <T> type of the examples
     */
    private static abstract class Checker<T> {
        private enum Decision {
            Allowed, Rejected
        }

        Checker<T> allows(@SuppressWarnings("unchecked") T... examples) {
            for (T example : examples) {
                check(example, is(Decision.Allowed));
            }
            return this;
        }

        Checker<T> rejects(@SuppressWarnings("unchecked") T... examples) {
            for (T example : examples) {
                check(example, is(Decision.Rejected));
            }
            return this;
        }

        private void check(T example, org.hamcrest.Matcher<Decision> matcher) {
            assertThat("'" + example + "' Decision", decideOn(example), matcher);
        }

        private Decision decideOn(T example) {
            if (isAllowed(example)) {
                return Decision.Allowed;
            }
            return Decision.Rejected;
        }

        protected abstract boolean isAllowed(T example);
    }
}
