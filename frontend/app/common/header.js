import { View,  TouchableOpacity, Image, StyleSheet, Text } from "react-native";
import { Stack, useRouter } from "expo-router";
import icons from "../../constants/icons";
import { AuthStore } from "../../store";


const Header = () => {
  const router = useRouter();

  const { isLoggedIn } = AuthStore.useState((s) => s);
  return (
    <Stack.Screen
            options={{
              title: "Overview",
              headerShown: isLoggedIn,
              headerRight: () => (
                <View style={styles.container}>
                <TouchableOpacity
                  onPress={() => {
                    AuthStore.update((s) => {
                      s.isLoggedIn = false;
                    });
                    router.replace("/home");
                  }}
                >
                    <Text style={styles.logoutText}>LOGOUT</Text>
                </TouchableOpacity>
                 <TouchableOpacity
                 onPress={() => {
                 }}
               >
                    <Image
                        source={icons.notification}
                        resizeMode='contain'
                        style={styles.icon}
                    />
               </TouchableOpacity>
               </View>
              ),
            }}
          />
  )
}

const styles = StyleSheet.create({
    container: {
      flexDirection: 'row',
      justifyContent: 'flex-end',
      alignItems: 'center',
      padding: 16,
    },
    button: {
      padding: 8,
    },
    icon: {
      width: 24,
      height: 24,
      marginLeft:20,
    },
  });

export default Header;