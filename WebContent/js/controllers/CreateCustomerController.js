mainApp.controller("CreateCustomerController", function ($scope, $http, $templateCache) {
     $scope.cust={"SSN_ID":"","name":"","age":"","adl1":"","adl2":"","city":"","state":""};
     $scope.cust.su=false;
     $scope.cust.form1=true;
     $scope.cust.creating=false;
     
   //write function for validating then run function only when validations are correct
     $scope.cust.submitForm = function() {

         // check to make sure the form is completely valid
    	 //everything is correct then run function from it
         if ($scope.userForm.$valid) {
             
             console.log('user form is valid');
             //run create Customer function
             $scope.cust.createCustomer();
         }

     };
     
   //write function for validating then run function only when validations are correct
     $scope.cust.submitForm1 = function() {

         // check to make sure the form is completely valid
    	 //everything is correct then run function from it
         if ($scope.CCForm.$valid) {
             
             console.log('user form is valid');
             //run create Customer function
             $scope.cust.createCustomer();
         }

     };
     
   
     
      $scope.cust.createCustomer = function() {
      $scope.cust.creating=true;
      $scope.cust.form1=true;
      $scope.code = null;
      $scope.response = null;
      $scope.cust.su=false;
      var data1=$scope.cust;
      console.log('SSN_ID :'+data1.SSN_ID);
      console.log('Name :'+data1.name);
      console.log('Age :'+data1.age);
      console.log('Addresh line 1 :'+data1.adl1);
      //use $ sign to split between address variables
      var add= data1.adl1+"$"+  data1.adl2 +"$"+ data1.city +"$"+ data1.state;
      console.log('Address is : '+add);

      $http({method: 'POST', url: '/ABC_Retail_Banking/createCust',data: { SSN_ID: data1.SSN_ID, name: data1.name, age: data1.age, address: add }, cache: $templateCache}).
        then(function(response) {
          $scope.status = response.status;
          if($scope.status===200){
        	  $scope.cust.message='User Created Succesfully with Customer_ID : '+response.data.Customer_ID;
        	  $scope.cust.su=true;
        	  $scope.cust.form1=false;
        	  $scope.cust.creating=false;
        	  console.log('Customer_ID is : '+response.data.Customer_ID);
        	  
          }
          $scope.data = response.data;
          $scope.cust.message1=response.data.message;
        }, function(response) {
          $scope.data = response.data || 'Request failed';
          $scope.status = response.status;
      });
    };
      
});
