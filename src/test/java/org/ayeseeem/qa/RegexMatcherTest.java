package org.ayeseeem.qa;

import static org.ayeseeem.qa.RegexMatcher.matches;
import static org.ayeseeem.qa.RegexMatcher.misses;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

import java.util.regex.Pattern;

import org.junit.Test;

public class RegexMatcherTest {

    @Test
    public void testMatches() {
        Pattern digits = Pattern.compile("^[0-9]+$");

        assertThat(digits, matches("1", "123", "1234567890"));

        AssertionError er = assertThrows(AssertionError.class, () -> {
            assertThat(digits, matches("123", "AAA", "BBB", "456"));
        });
        assertThat(er.getMessage(), containsString("Expected: All cases to match"));
        assertThat(er.getMessage(), containsString("but: these miss: <[AAA, BBB]>"));
        assertThat(er.getMessage(), not(containsString("123")));
        assertThat(er.getMessage(), not(containsString("456")));
    }

    @Test
    public void testMisses() {
        Pattern digits = Pattern.compile("^[0-9]+$");

        assertThat(digits, misses("A", "ABC", "ABC..XYZ"));

        AssertionError er = assertThrows(AssertionError.class, () -> {
            assertThat(digits, misses("ABC", "111", "222", "DEF"));
        });
        assertThat(er.getMessage(), containsString("Expected: All cases to miss"));
        assertThat(er.getMessage(), containsString("but: these match: <[111, 222]>"));
        assertThat(er.getMessage(), not(containsString("ABC")));
        assertThat(er.getMessage(), not(containsString("DEF")));
    }

}
