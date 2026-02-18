# Exclude these files from the build process
@default_excluded_files = ('unittests.tex', 'uml_diagram.tex', 'task1test1.tex', 'task1test2.tex', 'task1test3.tex', 'task1test4.tex', 'task1test5.tex', 'task2test1.tex', 'task2test2.tex', 'task3test1.tex', 'task3test2.tex', 'task3test3.tex', 'task4test1.tex', 'task4test2.tex', 'task4test3.tex', 'task4test4.tex', 'task4test5.tex', 'task4test6.tex', 'task5test1.tex', 'task5test2.tex');

# Set the default PDF mode to LuaLaTeX
$pdf_mode = 4;

# Optional: Set the specific command with desired options
$lualatex = 'lualatex --shell-escape --file-line-error %O %S';
