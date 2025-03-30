---
layout: page
title: User Guide
---

QuickHire is a desktop address book application designed for recruiters to manage and organise the details of their potential job candidates. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2425S2-CS2103T-T16-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your QuickHire application.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar quickhire.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 j/Software Engineer l/Unreviewed` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Items **starting with** `/` (e.g., `/a`, `/f`, etc.) are to specified as they are without any parameters.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS j/JOB TITLE l/LABEL [s/INTERVIEW_SCHEDULE] [r/REMARK] [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A label can only be Unreviewed, Shortlisted, Rejected or Accepted.</br>
A person can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 s/ j/Data Scientist l/Unreviewed r/Likes to code t/friends t/owesMoney`
* `add n/Vish p/1293123 e/sample@domain.com a/213123 street j/ProData guy l/Rejected`

### Listing all persons : `list`

Shows a list of all persons in the address book.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
By default, only basic contact details are displayed, to all applicable roles that the candidate is applying for, use the `view` command.
</div>

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [j/JOB TITLE] [l/LABEL] [s/INTERVIEW_SCHEDULE] [r/REMARK] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.
* You can remove a person’s remarks by typing `r/` without
  specifying any remarks after it.
* You can remove a person’s interview schedule by typing `s/` without
  specifying any date time after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com s/10-02-2025 9:00` Edits the phone number, email address and interview schedule of the 1st person to be `91234567`, `johndoe@example.com`, `10-02-2025 9:00` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.
*  `edit 1 r/` Clears all remarks for the 1st person
*  `edit 1 l/Shortlisted` Updates the label of the 1st person to `Shortlisted`

### Viewing a person's full application details: `view`

Displays the full application details of

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
By default, the application will show the full application details of the first person, if any. 
</div>

Format: `view INDEX`

* Details shown include job titles of roles candidate has applied for, status of job application and any additional remarks provided 

Example:
*  `view 1` Displays the full information of the first person in the side panel

### Locating persons by name: `find`

Finds persons whose details contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* All details of a person are searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Adding remarks to a person : `remark`

Note: The functionalities of this command can be achieved via the `r/REMARK` flag in `add` and `edit` commands.

Format: `remark INDEX r/REMARK`

* Adds a remark to the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* Existing values will be updated to the input values.
* You can remove a person’s remarks by typing `r/` without
  specifying any remarks after it.

Examples:
*  `remark 1 r/Likes to code` Adds a remark (`Likes to code`) to the 1st person
*  `remark 1 r/` Clears all remarks for the 1st person

### Saving the data : `save`

QuickHire data is saved in the hard disk automatically after any command that changes the data.

However, users can choose to save this data to a file of their choice with this command.

Format: `save p/PATH_TO_FILE [/a] [/f]`

* Saves the filtered QuickHire data (filtered using the `find` command) into the file at the specified location `PATH_TO_FILE`.
* By default, if the file at `PATH_TO_FILE` already exists, then no data will be overwritten to that file.
* (Optional) Specify `/f` to overwrite the contents of the file specified
* (Optional) Specify `/a` to save all QuickHire data (instead of just the filtered ones).
* The applicaton needs to have sufficient permissions to write to the file in order for the `save` feature to work.

Examples:
* `save p/past_candidates.json` Saves the current list of (filtered) candidates in to `[JAR file location]/past_candidates.json`
* `save /a p//all_candidates.json` Save all candidates in the application to `/all_candidates.json`
* `save p/existing_file.json /a /f` Save all candidates in the applicatoin to `[JAR file location]/exiting_file.json` and overwrites any existing data in the file

### Viewing job application statistics: 'viewstats'

Displays the number of applications for each role.

Format: `viewstats`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Editing the data file

QuickHire data is saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: What if the same person applies to the same company a few months later?<br>
**A**: There are 2 options:
1. Edit the person's existing entry with the updated details; or
1. Delete the old entry, and re-add the complete and updated details of the person (should there be any clashes in data)

**Q**: What if the person wants to apply for multiple roles within the same company? <br>
**A**: Multiple job titles can be added using the edit command.

**Q**: What if there are multiple stages of interview, how should I save it? <br>
**A**: Once one stage of the interview is completed, use the edit command to add the next interview date to override the current interview date.

**Q**: I have details of 37 candidates saved in the app. But when I run `save`, the file only has details of 2 candidates. Why is this so? <br>
**A**: Probably the `save` command was executed without any optional flags. To be able to save all data, you have 2 options:
1. (Easiest) Use the optional `/a` flag of `save` command to save all candidates' information.
   E.g., `save p/file_to_save.json /a`
1. Run `list` in the app to ensure the app is not displaying any _filtered_ data. Then run the `save` command as usual.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
1. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Flags summary

| Action | Description             | Used in (command)       | Example(s)                          | Mandatory? |
|--------|-------------------------|-------------------------|-------------------------------------|------------|
| **n/** | `NAME`                  | `add`, `edit`           | `n/John`                            | Yes        |
| **p/** | `PHONE NUMBER`          | `add`, `edit`           | `p/91234567`                        | Yes        |
| **e/** | `EMAIL`                 | `add`, `edit`           | `e/john@example.com`                | Yes        |
| **a/** | `ADDRESS`               | `add`, `edit`           | `a/21, Kent Street, 123123`         | Yes        |
| **j/** | `JOB SCOPE`             | `add`, `edit`           | `j/Software Engineering Intern`     | Yes        |
| **l/** | `LABEL`                 | `add`, `edit`           | `l/Unreviewed`                      | Yes        |
| **p/** | `PATH TO FILE`          | `save`                  | `p/candidates.json`                 | Yes        |
| **s/** | `INTERVIEW SCHEDULE`    | `add`, `edit`           | `s/12-04-2025 13:00`                | No         |
| **r/** | `REMARK`                | `add`, `edit`, `remark` | `r/Amazing fit for company culture` | No         |
| **t/** | `TAGS`                  | `add`, `edit`           | `t/Java`                            | No         |
| **/a** | Save all data           | `save`                  | `/a`                                | No         |
| **/f** | Overwrite existing file | `save`                  | `/f`                                | No         |

## Command summary

| Action        | Format                                                                                                                    | Example(s)                                                                                                                                           |
|---------------|---------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**       | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS j/JOB TITLE l/LABEL [s/INTERVIEW_SCHEDULE] [r/REMARK] [t/TAG]…​`             | `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 j/Software Engineer l/Unreviewed s/10-02-2025 10:00 r/Likes to code` |
| **Clear**     | `clear`                                                                                                                   |                                                                                                                                                      |
| **Delete**    | `delete INDEX`                                                                                                            | `delete 3`                                                                                                                                           |
| **Edit**      | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [j/JOB TITLE] [l/LABEL] [s/INTERVIEW_SCHEDULE] [r/REMARK] [t/TAG]…​` | `edit 2 n/James Lee e/jameslee@example.com`                                                                                                          |
| **Find**      | `find KEYWORD [MORE_KEYWORDS]`                                                                                            | `find James Jake`                                                                                                                                    |
| **Remark**    | `remark INDEX r/REMARK`                                                                                                   | `remark 1 r/Has experience using JEE`, `remark 7 r/`                                                                                                 |
| **Save**      | `save p/PATH_TO_FILE [/a] [/f]`                                                                                           | `save p/past_candidates.json`, `save p/exiting_file.json /f`, `save /a p/all_candidates.json`                                                        |
| **ViewStats** | `viewstats`                                                                                                               |                                                                                                                                                      |
| **View**      | `view INDEX`                                                                                                              | `view 5`                                                                                                                                             |
| **List**      | `list`                                                                                                                    |                                                                                                                                                      |
| **Help**      | `help`                                                                                                                    |                                                                                                                                                      |
