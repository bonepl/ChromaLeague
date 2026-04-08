@echo off
REM Chroma League - Node.js sidecar launcher
REM %~dp0 resolves to this script's directory at runtime

REM 1. Try bundled node.exe first (distributed inside .opk)
if exist "%~dp0node\node.exe" (
    start /B "" "%~dp0node\node.exe" "%~dp0dist\server.js"
    exit /b 0
)

echo [Chroma League] Fatal error: Node.js not found
exit /b 1
