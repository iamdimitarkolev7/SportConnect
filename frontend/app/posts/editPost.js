import { Text, View, TextInput, StyleSheet, ActivityIndicator, TouchableOpacity, SafeAreaView, KeyboardAvoidingView } from "react-native";
import SelectDropdown from 'react-native-select-dropdown';
import {Calendar, LocaleConfig} from 'react-native-calendars';
import { useRef, useState } from "react";
import { Stack, useRouter } from "expo-router";
import Footer from "../common/footer";
import Header from "../common/header";
import { ScrollView } from "react-native-gesture-handler";

const EditPost = () => {
  const router = useRouter();
  const titleRef = useRef("");
  const categories = ["Footabll", "Volleyball", "Basketball", "Tennis", "Table tennis", "Squash", "Other"];
  const categoryRef = useRef("");
  const descriptionRef = useRef("");
  const [selectedDate, setSelectedDate] = useState('');
  //const ownerRef = currentUser
  return (
    <SafeAreaView style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
      <Header />
      <KeyboardAvoidingView behavior={Platform.OS === 'ios' ? 'padding' : 'height'} style={styles.container}>
        <ScrollView contentContainerStyle={{ flexGrow: 1 }} marginTop={20}>
        <View>
          <Text style={styles.label}>Title</Text>
          <TextInput
            placeholder="Title"
            nativeID="title"
            onChangeText={(text) => {
              titleRef.current = text;
            } }
            style={styles.textInput} />
        </View>

        

        <View>
          <Text style={styles.label}>Category</Text>
          <SelectDropdown
            data={categories}
            onSelect={(selectedItem, index) => {
              categoryRef.current = selectedItem;
            } } />
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
            } }
            style={styles.textInput} />
        </View>

        <View>
          <Text style={styles.label}>Date</Text>
          <Calendar
            onDayPress={day => {
              setSelectedDate(day.dateString);
            } }
            markedDates={{
              [selectedDate]: { selected: true, disableTouchEvent: true, selectedDotColor: 'orange' }
            }} />
        </View>     

        <View>
          <TouchableOpacity
            onPress={() => {
              //TODO... create post
              router.replace("/home");
            } }>
            <Text>Create Post</Text>
          </TouchableOpacity>

          <TouchableOpacity onPress={() => { router.replace("/home"); } }>
            <Text>Cancel</Text>
          </TouchableOpacity>
        </View> 
        </ScrollView>
    </KeyboardAvoidingView>
    
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

export default EditPost;