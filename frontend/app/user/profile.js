import { View, Text, ActivityIndicator, TouchableOpacity, SafeAreaView  } from "react-native";
import { Stack, useRootNavigationState } from "expo-router";
import { useRouter, useSegments } from "expo-router";
import React from "react";
import Footer from "./../common/footer";
import Header from "./../common/header";

export default function Profile() {
  const router = useRouter();
  const navigationState = useRootNavigationState();

  return (
    <SafeAreaView  style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
        <Header/>
      {!navigationState?.key ? (
        <ActivityIndicator size="large" />
      ) : (
        <View>
            <Text>Profile page</Text>
        </View>
      )}
      <Footer/>
    </SafeAreaView >
  );
}