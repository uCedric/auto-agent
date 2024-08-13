FROM node:18 AS builder
WORKDIR /app
COPY ["package.json", "package-lock.json", "./"]
RUN npm run build

FROM node:18 
WORKDIR /app
COPY --from=builder /app/node_modules ./node_modules
COPY ["package.json", "server.js", "app.js", "./"]
COPY src src
COPY utils utils
ENTRYPOINT ["npm", "start"]