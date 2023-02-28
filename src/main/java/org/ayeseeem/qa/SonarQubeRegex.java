package org.ayeseeem.qa;

/**
 * Defines some of the SonarQube rules' regular expressions, for documentation
 * purposes, and to allow testing.
 */
public class SonarQubeRegex {

    /**
     * Default SonarQube rule for Constant names: only single consecutive
     * underscores allowed. Defined as {@value}.
     */
    public static final String S00115__CONSTANT_NAMES__DEFAULT__STR = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";

    /**
     * Our custom SonarQube rule for Constant names: double consecutive underscores
     * allowed, to allow separation of name and emerging type. Defined as {@value}.
     */
    public static final String S00115__CONSTANT_NAMES__ICM__STR = "^[A-Z][A-Z0-9]*(_{1,2}[A-Z0-9]+)*$";

    /**
     * Default SonarQube rule for Type Parameter names: single capital letter plus
     * optional number. Defined as {@value}.
     */
    public static final String S00119__TYPE_PARAM_NAMES__DEFAULT__STR = "^[A-Z][0-9]?$";

    /**
     * Our custom SonarQube rule for Type Parameter names
     * ({@link #S00119__TYPE_PARAM_NAMES__DEFAULT__STR}, or one or more capitalized
     * words followed by T, to allow roles to be included, for example
     * {@code ProviderT}, Defined as {@value}.
     */
    public static final String S00119__TYPE_PARAM_NAMES__ICM__STR = "(" + S00119__TYPE_PARAM_NAMES__DEFAULT__STR + ")"
            + "|" + "(^(([A-Z][a-z0-9]+)+T)$)";

}
