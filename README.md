## Overview

EnglishHelper is an application that can help people learn new English words.

The user adds words they want to know, and after that, the application will check whether the user remembers them.
If yes, the word's level is increased, else the level is decreased. With every new level, the duration between checks
is increased. It makes sense because as worse you remember the word, the more often you need to repeat it.

## Screens


| <br/><img src="https://github.com/yakuraion/EnglishHelper/blob/master/attachments/overview.jpg" width="250" /><br/>**Overview the current state of learning**<br/>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; |   <br/><img src="https://github.com/yakuraion/EnglishHelper/blob/master/attachments/addwords.jpg" width="250" /><br/>**Adding English words to learn**<br/>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;    |
|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
|            <br/><img src="https://github.com/yakuraion/EnglishHelper/blob/master/attachments/testing.gif" width="250" /><br/>**Testing the words**<br/>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;            | <br/><img src="https://github.com/yakuraion/EnglishHelper/blob/master/attachments/listwords.jpg" width="250" /><br/>**Tracking the progress of learning**<br/>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; |

## Architecture

The architecture is presented by a variation of Clean Architecture and is shown in the simplified scheme below.

<img src="https://github.com/yakuraion/EnglishHelper/blob/master/attachments/architecture.png" />

## Inner gradle plugins

#### conventions

List of gradle convention plugins that helps reusing the same logic across different build.gradle files.

#### code-check

A plugin that adds lint checks (Detekt only now) to the default `check` gradle task.

## Git submodule

This project works with the git submodules feature. Don't forget to do extra actions to retrieve these submodules:

`git clone --recurse-submodules https://github.com/yakuraion/EnglishHelper.git` - to clone the project.

`git submodule update --init --recursive` - to fetch submodules if you've already cloned the project in the usual way.
