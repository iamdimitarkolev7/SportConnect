import handleAuthResponse from "./utils/handleAuthResponse";
import handleError from "./utils/handleError";

//http://localhost:8081/api/v1/auth/register
const registerRequest = async (data) => {
  try {
    const response = await fetch('http://192.168.0.111:8081/api/v1/auth/register', {
      method: 'POST',
      body: JSON.stringify(data),
      headers: {
        'Content-type': 'application/json'
      },
      credentials:"include"
    });

    const result = await response.json();
    return result; // Връщаме резултата от заявката към сървъра
  } catch (error) {
    throw error; // Хвърляме грешката напред, за да я обработи функцията, която извика тази функция
  }
};

const loginRequest = async (data) => {
  try {
    const response = await fetch('http://192.168.0.111:8081/api/v1/auth/login', {
      method: 'POST',
      body: JSON.stringify(data),
      headers: {
        'Content-type': 'application/json'
      },
      credentials:"include"
    });

    const result = await response.json();
    return result; // Връщаме резултата от заявката към сървъра
  } catch (error) {
    throw error; // Хвърляме грешката напред, за да я обработи функцията, която извика тази функция
  }
};

const userRequests = { registerRequest, loginRequest };

export default userRequests;