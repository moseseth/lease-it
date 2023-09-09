FROM node:14-alpine AS build

WORKDIR /app

COPY package*.json ./

RUN npm install

ARG REACT_APP_API_ADDRESS
ENV REACT_APP_API_ADDRESS=$REACT_APP_API_ADDRESS

COPY . .

RUN npm run build

# Production stage
FROM nginx:1.21.1-alpine

COPY --from=build /app/build /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
