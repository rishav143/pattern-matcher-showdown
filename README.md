## Pattern Matcher Showdown

A simple Java Swing app that compares two classic string searching algorithms — Knuth–Morris–Pratt (KMP) and Boyer–Moore — by finding pattern matches and reporting their execution times.

### Features
- **GUI input**: Enter a source string and a target pattern.
- **Algorithms**: Runs both **KMP** and **Boyer–Moore**.
- **Results**: Shows all match positions and nanosecond timings, with a quick comparison of which was faster in that run.

### Requirements
- **Java 11+** (modules are used). Java 17+ recommended.

### Project Structure
```
src/
  module-info.java        # module name: GUI
  GUI/
    stringCompare.java    # main class with Swing UI
bin/                      # compiled classes (output)
```

### Build and Run
You can build/run as a Java module (recommended) or as a plain classpath app.

#### Option 1: Module-aware (recommended)
Compile:
```bash
javac -d bin src/module-info.java src/GUI/stringCompare.java
```

Run:
```bash
java --module-path bin --module GUI/GUI.stringCompare
```

#### Option 2: Plain classpath (if you prefer)
Compile:
```bash
javac -d bin src/GUI/stringCompare.java
```

Run:
```bash
java -cp bin GUI.stringCompare
```

### Using the App
1. Launch the app (see commands above).
2. Enter the **Source String** (the text to search in).
3. Enter the **Target String** (the pattern to find).
4. Click **compare** to see:
   - All match indices found by KMP and Boyer–Moore
   - Execution time for each algorithm (nanoseconds)
   - A quick note on which algorithm was faster in that run

### Notes
- Execution times are measured per run on your machine and will vary.
- For non-ASCII characters, Boyer–Moore here uses a simple last-occurrence table sized to 256 entries.

### Contributing
Feel free to submit PRs for improvements (bug fixes, UI polish, additional algorithms like Rabin–Karp, etc.).

### License
MIT
