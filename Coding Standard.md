Coding Standard
===============

Java
----

- Use 4 spaces for indenting, not tabs

### Spring STS/Eclipse ###

#### Java; Compiler; Errors/Warnings ####

Apply the following changes

- Code style
  - Undocumented empty block: Ignore -> Warning
  - Resource not managed via try-with-resource (1.7+): Ignore -> Error
- Potential programming problems
  - Possible accidental boolean assignment (e.g. if (a = b)): Ignore -> Error
  - Empty statement: Ignore -> Error
  - Class overrides 'equals()' but not 'hashCode()': Ignore -> Error
- Unnecessary code
  - Value of method parameter is not used: Ignore -> Warning
    (but Ignore in overriding and implementing methods)
  - Unnecessary 'else' statement: Ignore -> Warning
  - Unnecessary cast or 'instanceof' operation: Ignore -> Warning
  - Unnecessary declaration of thrown exceptions: Ignore -> Error
    (but Ignore in overriding and implementing, or documented with '@throws'
    or '@exception'; Don't ignore Exception or Throwable)
- Generic Types
  - Redundant type arguments (1.7 or higher): Ignore -> Warning


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

XML
---

- Use 2 or 4 spaces for indenting, not tabs.
  - Maven POM files are supposed to be indented with 2 spaces
    <https://maven.apache.org/developers/conventions/code.html#XML_Code_Style>,
    although that convention does not seem to be very common, and the
    Spring tools seem to use tab indentation.
