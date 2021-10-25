Coding Standard
===============

Allowed tags in source code (configure your IDE to detect them):

- "TODO": low-level tasks
  - for significant issues, put TODOs in READMEs or use an issue tracker
- "HACK": "temporary" hacks to get code running
  - avoid leaving these in the code for long

Add initials and time, like this:

```java
// TODO: ABC 2020-10-31: Thing to do
```

Strip trailing whitespace.

- Regex for Eclipse/Spring STS to find trailing whitespace
 (but prefer configuring the IDE to fix on save, at least for some file types):
  `[ \t]+$`


Java
----

- Use 4 spaces for indenting, not tabs


### Spring STS/Eclipse ###

Recommended plug-ins:

- [MoreUnit](https://moreunit.github.io/MoreUnit-Eclipse/)
- [Infinitest](https://infinitest.github.io/#eclipse)


#### Java; Compiler; Errors/Warnings ####

Apply the following changes

- Code style
  - Undocumented empty block: Ignore -> **Warning**
  - Resource not managed via try-with-resource (1.7 or higher): Ignore -> **Error**
- Potential programming problems
  - Possible accidental boolean assignment (e.g. 'if (a = b)'): Ignore -> **Error**
  - Empty statement: Ignore -> **Error**
  - Class overrides 'equals()' but not 'hashCode()': Ignore -> **Error**
- Unnecessary code
  - Value of method parameter is not used: Ignore -> **Warning**
    - (but Ignore in overriding and implementing methods)
  - Unnecessary 'else' statement: Ignore -> **Warning**
  - Unnecessary cast or 'instanceof' operation: Ignore -> **Warning**
  - Unnecessary declaration of thrown exception: Ignore -> **Error**
    - (but Ignore in overriding and implementing, or documented with '@throws'
      or '@exception'; **Don't** ignore Exception or Throwable)
- Generic types
  - Redundant type arguments (1.7 or higher): Ignore -> **Warning**


#### Java; Code Style; Formatter ####

Load [`Java--Code-Style--Formatter--STS-4--Eclipse-built-in--plus-mods.xml`](Java--Code-Style--Formatter--STS-4--Eclipse-built-in--plus-mods.xml),
which is the "Eclipse [built-in]" profile, with the following modifications
(changes in bold):


##### Indentation #####

- Tab policy: **Spaces only**


#### Java; Code Style; Clean Up ####

Load [`Java--Code-Style--Clean-Up--STS-4--Eclipse-built-in--plus-mods.xml`](Java--Code-Style--Clean-Up--STS-4--Eclipse-built-in--plus-mods.xml),
which is the "Eclipse [built-in]" profile, with the following modifications
(changes in bold):


##### Code Organising #####

- **Remove trailing whitespace**
- **Organize imports**


##### Code Style #####

- **Use blocks in if/while/for/do statements**
  - **Always**
- **Convert 'for' loops to enhanced**
- **Convert functional interface instances**
  - Use lambda where possible


##### Unnecessary Code #####

- Remove unused imports
- Remove unnecessary casts
- **Remove redundant type arguments**
- **Remove redundant modifiers**
- **Remove redundant semicolons**


#### Java; Editor; Save Actions ####

- **Perform the selected actions on Save**
- **Organize imports**
- Additional Actions:
  - Configure this to match the Clean Up (above) as far as possible


### JUnit ###

TODO: add more about Hamcrest:

- Prefer `assertThat` to `assertEquals`
- Use Hamcrest's `assertThat`, not JUnit's
- Regex for Eclipse/Spring STS to convert (simple, single-comma) assertions:
  - Find: `assertEquals\((.*), (.*)\)`
  - Replace: `assertThat\($2, is\($1\)\)`


XML
---

- Use 2 or 4 spaces for indenting, not tabs.
  - Maven POM files are supposed to be indented with 2 spaces
    <https://maven.apache.org/developers/conventions/code.html#XML_Code_Style>,
    although that convention does not seem to be very common, and the
    Spring tools seem to use tab indentation.
