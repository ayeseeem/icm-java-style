package org.ayeseeem.qa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

class RegexMatcher extends TypeSafeDiagnosingMatcher<Pattern> {

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
