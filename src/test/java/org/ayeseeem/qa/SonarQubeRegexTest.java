package org.ayeseeem.qa;

import static org.ayeseeem.qa.RegexMatcher.matches;
import static org.ayeseeem.qa.RegexMatcher.misses;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.regex.Pattern;

import org.junit.Test;

/**
 * Checks some of the SonarQube rules' regular expressions.
 */
public class SonarQubeRegexTest {

    @Test
    public void testConstantNaming_AllowDoubleUnderscores() {
        Pattern theirs = Pattern.compile(SonarQubeRegex.S00115__CONSTANT_NAMES__DEFAULT__STR);
        Pattern ours = Pattern.compile(SonarQubeRegex.S00115__CONSTANT_NAMES__ICM__STR);

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
    public void testTypeParameterNaming_ChangesFromSonarQubeDefault() {
        Pattern theirs = Pattern.compile(SonarQubeRegex.S00119__TYPE_PARAM_NAMES__DEFAULT__STR);
        Pattern ours = Pattern.compile(SonarQubeRegex.S00119__TYPE_PARAM_NAMES__ICM__STR);

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
