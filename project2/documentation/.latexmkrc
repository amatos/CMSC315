# Exclude these files from the build process
@default_excluded_files = ('tabledata.tex','sentence1.tex','sentence2.tex','sentence3.tex','sentence4.tex','sentence5.tex','sentence6.tex','sentence7.tex','unittests.tex');

# Set the default PDF mode to LuaLaTeX
$pdf_mode = 4;

# Optional: Set the specific command with desired options
$lualatex = 'lualatex --shell-escape --file-line-error %O %S';
