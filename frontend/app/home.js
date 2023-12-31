import { View, Text, ActivityIndicator, TouchableOpacity, SafeAreaView  } from "react-native";
import { Stack, useRootNavigationState } from "expo-router";
import { useRouter, useSegments } from "expo-router";
import React from "react";
import { AuthStore } from "../store";
import Footer from "./common/footer";
import Header from "./common/header";

export default function Home() {
  const segments = useSegments();
  const router = useRouter();
  const { isLoggedIn } = AuthStore.useState((s) => s);
  const navigationState = useRootNavigationState();

  React.useEffect(() => {
    if (!navigationState?.key) return;

    if (!isLoggedIn ) {
      router.replace("/auth/login");
    }
  }, [isLoggedIn, navigationState?.key]);

  return (
    <SafeAreaView  style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
      <Header/>
      {!navigationState?.key ? (
        <ActivityIndicator size="large" />
      ) : (
        <View>
          
          <View>
          <Text>Go to Details</Text>
          <Text>More information</Text>
          </View>
        </View>
      )}
      <Footer />
    </SafeAreaView >
  );
}