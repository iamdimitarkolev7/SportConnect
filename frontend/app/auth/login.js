import { Text, View, TextInput, StyleSheet, ActivityIndicator, TouchableOpacity } from "react-native";
import { useRef } from "react";
import { AuthStore } from "../../store.js";
import { Stack, useRouter } from "expo-router";

export default function LogIn() {
  const router = useRouter();
  const emailRef = useRef("");
  const passwordRef = useRef("");
  return (
    <View style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
      <Stack.Screen options={{ title: "Login" }} />
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
        //TODO... login user
        AuthStore.update((s) => {
          s.isLoggedIn = true;
        });
        router.replace("/home");
      }}>
        <Text>Login</Text>
      </TouchableOpacity>
      <TouchableOpacity
      onPress={() => {
        router.replace("/auth/register");
      }}>
        <Text>Register</Text>
      </TouchableOpacity>
    </View>
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