import { Text, View, TextInput, StyleSheet, ActivityIndicator, TouchableOpacity, SafeAreaView  } from "react-native";
import { useRef } from "react";
import { AuthStore } from "../../store.js";
import { Stack, useRouter } from "expo-router";
import userRequests from "../../hook/userFetch.js";

export default function CreateAccount() {
  const router = useRouter();
  const emailRef = useRef("");
  const usernameRef = useRef("");
  const passwordRef = useRef("");
  const confirmPasswordRef = useRef("");
  const firstNameRef = useRef("");
  const lastNameRef=useRef("");

  async function handleRegistration() {
    const data = {
      "username":usernameRef.current,
      "email":emailRef.current,
      "password":passwordRef.current,
      "confirmPassword":confirmPasswordRef.current,
      "firstName":firstNameRef.current,
      "lastName":lastNameRef.current
    };
    try {
      const response = await userRequests.registerRequest(data);
  
      if(data.success){
        console.log("Регистрацията е успешна!", response);
        router.replace("/auth/login");
      }else{
        console.log("Ivalid! ", response);
      }
      
    } catch (error) {
      console.error("Грешка при регистрацията:", error);
    }
  }

  return (
    <SafeAreaView  style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
      <Stack.Screen
        options={{ title: "Create Account", headerLeft: () => <></> }}
      />
      <View>
        <Text style={styles.label}>Email</Text>
        <TextInput
          placeholder="email"
          nativeID="email"
          onChangeText={(text) => {
            emailRef.current = text;
          }}
          style={styles.textInput}
        />
      </View>
      <View>
        <Text style={styles.label}>Username</Text>
        <TextInput
          placeholder="username"
          nativeID="username"
          onChangeText={(text) => {
            usernameRef.current = text;
          }}
          style={styles.textInput}
        />
      </View>
      <View>
        <Text style={styles.label}>Password</Text>
        <TextInput
          placeholder="password"
          secureTextEntry={true}
          nativeID="password"
          onChangeText={(text) => {
            passwordRef.current = text;
          }}
          style={styles.textInput}
        />
      </View>
      <View>
        <Text style={styles.label}>Confirm Password</Text>
        <TextInput
          placeholder="confirm password"
          secureTextEntry={true}
          nativeID="confirmPassword"
          onChangeText={(text) => {
            confirmPasswordRef.current = text;
          }}
          style={styles.textInput}
        />
      </View>
      <View>
        <Text style={styles.label}>First Name</Text>
        <TextInput
          placeholder="first name"
          secureTextEntry={true}
          nativeID="firstName"
          onChangeText={(text) => {
            firstNameRef.current = text;
          }}
          style={styles.textInput}
        />
      </View>
      <View>
        <Text style={styles.label}>Last Name</Text>
        <TextInput
          placeholder="last name"
          secureTextEntry={true}
          nativeID="lastName"
          onChangeText={(text) => {
            lastNameRef.current = text;
          }}
          style={styles.textInput}
        />
      </View>

      <TouchableOpacity
      onPress={() => {
        handleRegistration();
      }}>
      <Text>Register</Text>
      </TouchableOpacity>

      <TouchableOpacity
      onPress={() => {
        router.replace('/home');
      }}>
      <Text>CANCEL</Text>
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