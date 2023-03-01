# Base image
FROM node:14-alpine

# Set working directory
WORKDIR ./app

# Copy the rest of the application
COPY . .

# Copy package.json and package-lock.json
# COPY package*.json ./

# Install dependencies
RUN npm install

# Build the application
RUN npm run build

# Expose port 3000
EXPOSE 8081

# Start the application
CMD ["npm", "run", "serve"] 