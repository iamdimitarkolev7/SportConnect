import { useRootNavigationState } from "expo-router";
import { useRouter, useSegments } from "expo-router";
import { AuthStore } from "../store";
import React from "react";
import { Text, View, ActivityIndicator  } from "react-native";

const Index = () => {
  const segments = useSegments();
  const router = useRouter();
  const { isLoggedIn } = AuthStore.useState((s) => s);
  const navigationState = useRootNavigationState();

  React.useEffect(() => {
    if (!navigationState?.key) return;

    if (
      !isLoggedIn
    ) {
      // Redirect to the login page.
      router.replace("/auth/login");
    } else if (isLoggedIn) {
      // go to tabs root.
      router.replace("/home");
    }
  }, [isLoggedIn, , navigationState?.key]);

  return <View>{!navigationState?.key ? <ActivityIndicator size="large" /> : <></>}</View>;
};
export default Index;