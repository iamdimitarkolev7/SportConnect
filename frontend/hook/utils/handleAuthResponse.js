const handleAuthResponse = (response) => {

    const message = response.message;
  
    if (!response.success) {
      throw new Error(message);
    }
    
    console.log("succes\n");
  }
  
  export default handleAuthResponse;