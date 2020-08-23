const baseUrl = "http://localhost:8080";
export { login };

const login = (username, password) => {
  const requestOptions = {
    method: "POST",
    body: JSON.stringify({
      username,
      password,
    }),
  };
  const url = baseUrl + "/login";
  return fetch(url, requestOptions);
};
