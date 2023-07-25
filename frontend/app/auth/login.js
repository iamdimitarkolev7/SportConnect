import { Text, View, TextInput, StyleSheet, ActivityIndicator, TouchableOpacity, SafeAreaView  } from "react-native";
import { useRef } from "react";
import { AuthStore } from "../../store.js";
import { Stack, useRouter } from "expo-router";
import userRequests from "../../hook/userFetch.js";

export default function LogIn() {
  const router = useRouter();
  const inputRef = useRef("");
  const passwordRef = useRef("");

  async function handleLogin() {
    const data={"password":passwordRef.current};
    if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(inputRef.current))
    {
      data.email=inputRef.current;
    }else{
      data.username=inputRef.current;
    }

    try {
      const response = await userRequests.loginRequest(data);

      if(response.success){
        console.log("Success!", response);
        AuthStore.update((s) => {
          s.isLoggedIn = true;
        });
        router.replace("/home");
      
      }else{
        console.log("Ivalid username or password!", response);
      }
      
    } catch (error) {
      console.error("Error during login:", error);
    }
  }

  return (
    <SafeAreaView  style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
      <Stack.Screen options={{ title: "Login" }} />
      <View>
        <Text style={styles.label}>Email/Username</Text>
        <TextInput
          placeholder="Email/Username"
          nativeID="inputRef"
          onChangeText={(text) => {
            inputRef.current = text;
          }}
          style={styles.textInput}
        />
      </View>
      <View>
        <Text style={styles.label}>Password</Text>
        <TextInput
          placeholder="password"
          nativeID="password"
          onChangeText={(text) => {
            passwordRef.current = text;
          }}
          style={styles.textInput}
        />
      </View>
      <TouchableOpacity
      onPress={() => {
        handleLogin();
      }}>
        <Text>Login</Text>
      </TouchableOpacity>
      <TouchableOpacity
      onPress={() => {
        router.replace("/auth/register");
      }}>
        <Text>Register</Text>
      </TouchableOpacity>
    </SafeAreaView >
  );
}

const styles = StyleSheet.create({
  label: {
    marginBottom: 4,
    color: "#455fff",
  },
  textInput: {
    width: 250,
    borderWidth: 1,
    borderRadius: 4,
    borderColor: "#455fff",
    paddingHorizontal: 8,
    paddingVertical: 4,
    marginBottom: 8,
  },
});