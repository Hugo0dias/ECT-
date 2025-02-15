#!/bin/bash

# Executar o servidor
python3 server.py &
SERVER_PID=$!

# Executar o viewer
python3 viewer.py &
VIEWER_PID=$!

# Executar o student
python3 student.py &
STUDENT_PID=$!

# Aguardar até que todos os processos terminem
wait $SERVER_PID $VIEWER_PID $STUDENT_PID
