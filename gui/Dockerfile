# Pull base image.
FROM gui:v1

# Install xterm.
RUN add-pkg openjdk-11-jre

# Copy the start script.
COPY startapp.sh /startapp.sh

# Set the name of the application.
ENV APP_NAME="odt"