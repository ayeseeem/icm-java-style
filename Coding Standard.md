Coding Standard
===============

Java
----

- Use 4 spaces for indenting, not tabs

### Spring STS/Eclipse ###

#### Java; Code Style; Formatter ####

Load [`Java--Code-Style--Formatter--STS-4--Eclipse-built-in--plus-mods.xml`](Java--Code-Style--Formatter--STS-4--Eclipse-built-in--plus-mods.xml),
which is the "Eclipse [built-in]" profile, with the following modifications
(changes in bold):

##### Indentation

- Tab policy: **Spaces only**

#### Java; Code Style; Clean Up ####

Load [`Java--Code-Style--Clean-Up--STS-4--Eclipse-built-in--plus-mods.xml`](Java--Code-Style--Clean-Up--STS-4--Eclipse-built-in--plus-mods.xml),
which is the "Eclipse [built-in]" profile, with the following modifications
(changes in bold):

- None

XML
---

- Use 2 or 4 spaces for indenting, not tabs.
  - Maven POM files are supposed to be indented with 2 spaces
    <https://maven.apache.org/developers/conventions/code.html#XML_Code_Style>,
    although that convention does not seem to be very common, and the
    Spring tools seem to use tab indentation.
