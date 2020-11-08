@ECHO OFF
%JAVA_HOME%\bin\jlink --module-path "%JAVA_HOME%\jmods" --compress=2 --add-modules java.base,java.logging,java.naming,jdk.crypto.ec --no-header-files --no-man-pages --output .\target\cl-jre