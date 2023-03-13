Coding Standard
===============

Allowed tags in source code (configure your IDE to detect them):

- `TODO`: low-level tasks
  - for significant issues, put TODOs in READMEs or use an issue tracker
  - (Eclipse: Priority: Normal)
- `HACK`: "temporary" hacks to get code running
  - avoid leaving these in the code for long
  - (Eclipse: Priority: High)

Add initials and time, like this:

```java
// TODO: ABC 2020-10-31: Thing to do
```

Strip trailing whitespace.

- Regex for Eclipse/Spring STS to find trailing whitespace:
  `[ \t]+$`
  - but prefer configuring the IDE to fix on save, and/or use
    Spotless Maven plugin, at least for some file types.


Java
----

- Use 4 spaces for indenting, not tabs
- Simplify lambdas:
  - Remove `{ }` from around single liners.
  - Prefer method references where possible.
- Don't use `public` on interface methods.
  [JLS Method Declarations](https://docs.oracle.com/javase/specs/jls/se8/html/jls-9.html#jls-9.4)
  says this (our emphasis):

  > Every method declaration in the body of an interface is implicitly
  > public (§6.6). It is permitted, but **discouraged as a matter of style**,
  > to redundantly specify the public modifier for a method declaration in
  > an interface.

  - Eclipse regex to find contraventions (produces false positives):
    `(?s)interface.*public`


### Spring STS/Eclipse ###

Recommended plug-ins:

- [MoreUnit](https://moreunit.github.io/MoreUnit-Eclipse/)
- [Infinitest](https://infinitest.github.io/#eclipse)


#### Java; Compiler; Errors/Warnings ####

Apply the following changes:

- Code style:

  - Undocumented empty block: Ignore -> **Warning**
  - Resource not managed via try-with-resource (1.7 or higher): Ignore -> **Error**

- Potential programming problems:

  - Possible accidental boolean assignment (e.g. 'if (a = b)'): Ignore -> **Error**
  - Empty statement: Ignore -> **Error**
  - Class overrides 'equals()' but not 'hashCode()': Ignore -> **Error**

- Unnecessary code:

  - Value of method parameter is not used: Ignore -> **Warning**

    - Ticked: Ignore in overriding and implementing methods

  - Unnecessary 'else' statement: Ignore -> **Warning**
  - Unnecessary cast or 'instanceof' operation: Ignore -> **Warning**
    - Partially moot given that "Remove unnecessary casts" is set in "Clean up".
  - Unnecessary declaration of thrown exception: Ignore -> **Error**

    - Ticked: Ignore in overriding and implementing methods
    - Ticked: Ignore exceptions documented with '@throws' or '@exception'
    - **Untick**ed: Ignore Exception or Throwable

- Generic types:

  - Redundant type arguments (1.7 or higher): Ignore -> **Warning**

- Annotations:

  - Missing '@Override' annotation: Ignore -> **Warning**
    - Moot given that "Add missing '@Override' annotations" is set in "Clean up".
  - Missing '@Deprecated' annotation: Ignore -> **Warning**


#### Java; Compiler; Javadoc ####

To avoid unpleasant surprises with Javadoc when doing `mvn release`,
consider turning on warnings under "Process Javadoc comments"
(and keep the defaults of the rest, for example `public` visibility only)
as follows:

- Malformed Javadoc comments: Ignore -> **Warning**
  - (because `mvn javadoc:javadoc` treats them as warnings.)
- Missing Javadoc tags: Ignore -> **Error**
  - (because `mvn javadoc:javadoc` treats (some of) them as errors.)
- Missing Javadoc comments: Ignore
  - (Currently, `mvn` checks comments that are present, but not totally absent
    ones.)


#### Java; Code Style; Clean Up ####

Load [`Java--Code-Style--Clean-Up--STS-4--Eclipse-built-in--plus-mods.xml`](Java--Code-Style--Clean-Up--STS-4--Eclipse-built-in--plus-mods.xml),
which is the "Eclipse [built-in]" profile, with the following modifications
(changes in **bold**):

- Code Organising:

  - **Remove trailing whitespace**
  - **Organize imports**

- Code Style:

  - **Use blocks in if/while/for/do statements**
    - **Always**
  - **Convert 'for' loops to enhanced**
  - **Convert functional interface instances**
    - Use lambda where possible

- Unnecessary Code:

  - Remove unused imports
  - Remove unnecessary casts
  - **Remove redundant type arguments**
  - **Remove redundant modifiers**
  - **Remove redundant semicolons**


#### Java; Code Style; Formatter ####

Load [`Java--Code-Style--Formatter--STS-4--Eclipse-built-in--plus-mods.xml`](Java--Code-Style--Formatter--STS-4--Eclipse-built-in--plus-mods.xml),
which is the "Eclipse [built-in]" profile, with the following modifications
(changes in **bold**):


- Indentation:

  - Tab policy: **Spaces only**


#### Java; Editor; Save Actions ####

- **Perform the selected actions on Save**
- **Organize imports**
- Additional Actions:
  - Configure this to match the Clean Up (above) as far as possible


### JUnit ###

- Prefer `assertThat()` to `assertEquals()`
  - It's nicer to read, and tends to make it clear which is the expected
    value, and which is the actual.
    People naturally seem to write, `assertThat(actual, is(expected));`
- Regex for Eclipse/Spring STS to convert (simple, single-comma) assertions:
  - Find: `assertEquals\((.*), (.*)\)`
  - Replace: `assertThat\($2, is\($1\)\)`
- If using Hamcrest, prefer Hamcrest's `assertThat()` to JUnit's `assertThat()`,
  which is being deprecated.
  - It can give slightly better diagnostic messages, and is more future-proof.


### SonarQube ###

TODO: ICM 2023-02-18: Write Java style paragraphs explaining these, with these regexes, rather than have, or to reference, this separate SonarQube section

Some of the changes are tested by
[`SonarQubeRegexTest.java`](src/test/java/org/ayeseeem/qa/SonarQubeRegexTest.java).

- TODO: ICM 2023-02-18: Extract constants for the Regexes

Standard set-up, with the following modifications:

- Allow "utility" classes to have a default constructor.
  We don't see the point in adding code just to stop a class being instantiated:
  there doesn't seem to be any problem if it is.

  - Disable "Utility classes should not have public constructors" (squid:S1118).

- Allow double underscores in constants, so we can add "emergent types".
  If we start naming things like USE_EXCEL__OPTION, USE_TEXT__OPTION,
  the double underscore can highlight that a type (Option) is starting to emerge.

  - Modify "Constant names should comply with a naming convention" (squid:S00115)
    Change regex from:
    `^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$`
    to:
    `^[A-Z][A-Z0-9]*(_{1,2}[A-Z0-9]+)*$`

- Allow generic type names like `EntityT`, not just `T`, `E` and so on.

  - Modify "Type parameter names should comply with a naming convention" (squid:S00119)
    Change regex from:
    `^[A-Z][0-9]?$`
    to:
    `(^[A-Z][0-9]?$)|(^(([A-Z][a-z0-9]+)+T)$)`


XML
---

- Use 2 or 4 spaces for indenting, not tabs.
  - Maven POM files are supposed to be indented with 2 spaces
    <https://maven.apache.org/developers/conventions/code.html#XML_Code_Style>,
    although that convention does not seem to be very common, and the
    Spring tools seem to use tab indentation.
