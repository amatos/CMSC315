# Exclude these files from the build process
@default_excluded_files = ('unittests.tex', 'uml_diagram.tex', 'test1.tex', 'test2.tex', 'test3.tex', 'test4.tex', 'test5.tex', 'test6.tex', 'test7.tex', 'test8.tex', 'test9.tex', 'test10.tex', 'test11.tex', 'test12.tex', 'test13.tex', 'test14.tex', 'test15.tex');

# Set the default PDF mode to LuaLaTeX
$pdf_mode = 4;

# Optional: Set the specific command with desired options
$lualatex = 'lualatex --shell-escape --file-line-error %O %S';
