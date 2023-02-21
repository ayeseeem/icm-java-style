package org.ayeseeem.qa;

import static org.ayeseeem.qa.RegexMatcher.matches;
import static org.ayeseeem.qa.RegexMatcher.misses;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.regex.Pattern;

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

}
