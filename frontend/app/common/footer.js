import { View,  TouchableOpacity, Image, StyleSheet } from "react-native";
import { Stack, useRouter } from "expo-router";
import icons from "../../constants/icons";

const Footer = () => {
  const router = useRouter();
  return (
    <View style={styles.container}>
        <TouchableOpacity onPress={() => {router.replace("../home"); }}>
            <Image
                source={icons.home}
                resizeMode='contain'
                style={styles.icon}
            />
        </TouchableOpacity>
        <TouchableOpacity onPress={() => {router.replace("../posts/addPost");}}>
            <Image
                source={icons.addPost}
                resizeMode='contain'
                style={styles.icon}
            />
        </TouchableOpacity>
    </View>
  )
}

const styles = StyleSheet.create({
    container: {
      flexDirection: 'row',
      justifyContent: 'space-around',
      alignItems: 'center',
      backgroundColor: '#f5f5f5',
      height: 60,
      borderTopWidth: 1,
      borderTopColor: 'gray',
      paddingHorizontal: 16,
      position: 'absolute',
      bottom: 10,
      left: 0,
      right: 0,
    },
    icon: {
      width: 24,
      height: 24,
    },
  });

export default Footer;