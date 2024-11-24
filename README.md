# VarDoc: IntelliJ IDEA Plugin for Meaningful Variable Names

**VarDoc** is an IntelliJ IDEA plugin designed to help developers improve code readability and maintainability by suggesting more meaningful alternatives for unclear variable names.

## Features

- Identifies unclear variable names in your code.
- Displays a list of suggestions for better naming.
- Replaces the variable name with the selected suggestion.

## Usage

1. **Activate the plugin:** Navigate to a variable in your code.
2. **Invoke VarDoc:** Press **Alt + Enter** to bring up the `VarDoc` option in the Intention menu.
3. **Make a selection:** A popup with name suggestions will appear. Choose one from the list.
4. **Confirm:** Press **Enter** to replace the variable name with the selected suggestion.

## Requirements

- IntelliJ IDEA version 2022.1 or higher.
- JDK 11 or higher.
- IntelliJ Plugin SDK properly configured.

## Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/raphaeldichler/VarDocPlugin
   cd vardoc
   ```
2. Install the plugin
- Open IntelliJ IDEA.
- Navigate to **File > Settings > Plugins > Install Plugin from Disk**.
- **Select** the generated .zip file from the build/distributions folder.

3. **Restart** IntelliJ IDEA to activate the plugin.

## Examples

### Before:
```java
int num_a = 10;
```

### After:
```java
int countOfApples = 10;
```

## Contributors
[Raphael Dichler](https://github.com/raphaeldichler) \
[Mingqi Han](https://github.com/MuZiQiAM) \
[Tanbier Ahmed](https://github.com/Tanbier) \
[Kagan Durmus](https://github.com/apocalypse14) 


## License
This project is licensed under the **MIT** License.


