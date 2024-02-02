/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import * as React from 'react';
import type { PropsWithChildren } from 'react';
import {
  Alert,
  Button,
  Image,
  ImageBackground,
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  useColorScheme,
  View,
} from 'react-native';

import { NavigationContainer } from '@react-navigation/native';

import {
  Colors,
  DebugInstructions,
  Header,
  LearnMoreLinks,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';
import { createNativeStackNavigator } from '@react-navigation/native-stack';


const Stack = createNativeStackNavigator();

const MyStack = () => {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen
          name="Home"
          component={App}
          options={{title: 'Welcome'}}
        />
        <Stack.Screen name="Profile" component={ProfileScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
};


interface Props {
  navigation: any;
  route:any;
}

const HomeScreen = ({navigation}: Props) => {
  return (
    <Button
      title="Go to Jane's profile"
      onPress={() =>
        navigation.navigate('Profile', {name: 'Jane'})
      }
    />
  );
};
const ProfileScreen = ({navigation, route}:Props) => {
  return <Text>This is {route.params.name}'s profile</Text>;
};

type SectionProps = PropsWithChildren<{
  title: string;
}>;

function _onPressButton() {
  Alert.alert("You press the button!");
}

function Section({ children, title }: SectionProps): React.JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';
  return (
    <View style={styles.sectionContainer}>
      <Text
        style={[
          styles.sectionTitle,
          {
            color: isDarkMode ? Colors.white : Colors.black,
          },
        ]}>
        {title}
      </Text>
      <Text
        style={[
          styles.sectionDescription,
          {
            color: isDarkMode ? Colors.light : Colors.dark,
          },
        ]}>
        {children}
      </Text>
    </View>
  );
}


function App({navigation}: Props): React.JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  function toDetail() {
    navigation.navigate('Profile', {name: 'Jane'})
  }

  return (
    <SafeAreaView style={[backgroundStyle,]}>
      <StatusBar
        barStyle={isDarkMode ? 'light-content' : 'dark-content'}
        backgroundColor={backgroundStyle.backgroundColor}
      />
      <ScrollView
        contentInsetAdjustmentBehavior="automatic"
        style={backgroundStyle}>
        <Header />
        <View
          style={{
            backgroundColor: isDarkMode ? Colors.black : Colors.white,
          }}>

          <View style={[styles.nav_button_container]}>
            <Button onPress={
              () => {
                console.log('You tapped the button!');
                toDetail();
              }}
              title='Press me!' />
          </View>

          <Section title="Step One or More!">
            Edit <Text style={[styles.highlight, styles.red]}>App.tsx</Text> to change this
            screen and then come back to see your edits.
          </Section>
          <Section title="See Your Changes">
            <ReloadInstructions />
          </Section>


          <View style={[styles.container, { flexDirection: 'column', height: 200, backgroundColor: 'powderblue' }]}>
            <View style={{ flex: 1, backgroundColor: 'red', width: 10, alignSelf: 'center' }} />
            <View style={{ flex: 2, backgroundColor: 'darkorange', width: 20 }} />
            <View style={{ flex: 3, backgroundColor: 'green', width: 30 }} />
          </View>

          <Image style={[{ width: 150, height: 150 }]} source={require('./images/test.jpg')} />

          <Image source={{ uri: 'https://reactjs.org/logo-og.png' }} style={{ width: 400, height: 400 }} />

          <ImageBackground style={[{ width: 150, height: 150 }]} source={require('./images/test.jpg')}>
            <Text style={{ color: "white" }}>Inside background.........Also here</Text>
          </ImageBackground>

          <Section title="Debug">
            <DebugInstructions />
          </Section>
          <Section title="Learn More">
            Read the docs to discover what to do next:
          </Section>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
  },
  highlight: {
    fontWeight: '700',
  },
  red: {
    color: '#FF0000',
  },
  container: {
    flex: 1,
    // padding: 20,
    margin: 8
  },
  nav_button_container: {
    width: '50%',
    margin: 8,
    alignSelf: 'center'
  },
});

// export default App;
export default MyStack;
