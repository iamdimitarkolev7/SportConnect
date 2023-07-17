import { Text, View, TextInput, StyleSheet, ActivityIndicator, TouchableOpacity, SafeAreaView } from "react-native";
import SelectDropdown from 'react-native-select-dropdown';
import { useRef } from "react";
import { Stack, useRouter } from "expo-router";
import Footer from "../common/footer";

const AddPost = () => {
  const router = useRouter();
  const titleRef = useRef("");
  const categories = ["Footabll", "Volleyball", "Basketball", "Tennis", "Table tennis", "Squash", "Other"];
  const categoryRef = useRef("");
  const descriptionRef = useRef("");
  //const ownerRef = currentUser
  return (
    <SafeAreaView  style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
      <Stack.Screen options={{ title: "Add Post" }} />

      <View>
        <Text style={styles.label}>Title</Text>
        <TextInput
          placeholder="title"
          nativeID="title"
          onChangeText={(text) => {
            titleRef.current = text;
          }}
          style={styles.textInput}
        />
      </View>

      <View>
      <Text style={styles.label}>Category</Text>
      <SelectDropdown
	        data={categories}
	        onSelect={(selectedItem, index) => {
		        categoryRef.current=selectedItem;
	    }}
        />
      </View>

      <View>
        <Text style={styles.label}>Description</Text>
        <TextInput
          multiline={true}
          numberOfLines={4}
          placeholder="description"
          nativeID="description"
          onChangeText={(text) => {
            descriptionRef.current = text;
          }}
          style={styles.textInput}
        />
      </View>

      <View>
      <TouchableOpacity
        onPress={() => {
            //TODO... create post
            router.replace("/home");
        }}>
        <Text>Create Post</Text>
      </TouchableOpacity>

      <TouchableOpacity onPress={() => {router.replace("/home");}}>
        <Text>Cancel</Text>
      </TouchableOpacity>
      </View>

      <Footer/>
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

export default AddPost;