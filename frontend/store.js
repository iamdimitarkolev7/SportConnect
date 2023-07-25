import { Store, registerInDevtools } from "pullstate";

export const AuthStore = new Store({
  isLoggedIn: false,
  token:"",
  refreshToken:""
});

registerInDevtools({AuthStore});