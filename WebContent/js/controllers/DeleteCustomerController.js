mainApp.controller("DeleteCustomerController", function ($scope, $http, $templateCache) {
     $scope.cust={"SSN_ID":"","name":"","age":"","adl1":"","adl2":"","city":"","state":""};
     
     $scope.cust.fetchresult=true;
     $scope.cust.deleteresult=false;
     $scope.cust.fetching=false;
     $scope.cust.deleting=false;
     $scope.cust.failFetching=false;
     $scope.cust.failDeleting=false;
     $scope.cust.noRecord=false;
     $scope.cust.deletedSuccess=false;
     // function for fetching results starts here
     $scope.cust.fetchResult = function() {
         $scope.cust.fetching=true;
         $scope.cust.form1=true;
         $scope.code = null;
         $scope.response = null;
         $scope.cust.su=false;
         var data1=$scope.cust;
         console.log('SSN_ID :'+data1.SSN_ID);
         console.log('Customer ID :'+data1.customer_ID);
       
         

         $http({method: 'POST', url: '/ABC_Retail_Banking/getCust',data: { SSN_ID: data1.SSN_ID,customer_ID:data1.customer_ID }, cache: $templateCache}).
           then(function(response) {
             $scope.status = response.status;
             console.log('value of err is :'+response.data.err);
             if((response.data.err==='true')){
            	 $scope.cust.noRecord=true;
            	 $scope.cust.fetching=false;
             }
             if(($scope.status===200) &&(response.data.err==='false')){
           	  $scope.cust.message='Customer data Succesfully fetched Customer_ID : '+response.data.customer_ID;
           
           	  $scope.cust.fetching=false;
           	  $scope.cust.fetchresult=false;
           	$scope.cust.SSN_ID=response.data.SSN_ID;
           	$scope.cust.customer_ID=response.data.customer_ID;
           	$scope.cust.age=response.data.age;
           	$scope.cust.name=response.data.name;
           	$scope.cust.adl1=response.data.adl1;
           	$scope.cust.adl2=response.data.adl2;
           	$scope.cust.city=response.data.city;
           	$scope.cust.state=response.data.state;
           	  $scope.cust.deleteresult=true;
           	  console.log('Customer_ID after fetch result is  : '+response.data.customer_ID);
           	console.log('message after fetch result is  : '+response.data.message);
             }
             $scope.data = response.data;
             $scope.cust.message1=response.data.message;
           }, function(response) {
             $scope.data = response.data || 'Request failed';
             $scope.status = response.status;
             $scope.cust.failFetching=true;
         });
       };
    // function for fetching results ends here
     
      $scope.cust.deleteCustomer = function() {
      $scope.cust.deleting=true;
      $scope.cust.form1=true;
      $scope.code = null;
      $scope.response = null;
      $scope.cust.su=false;
      var data1=$scope.cust;
      console.log('SSN_ID :'+data1.SSN_ID);
      console.log('customer_ID :'+data1.customer_ID);
      console.log('Name :'+data1.name);
      console.log('Age :'+data1.age);
      console.log('Addresh line 1 :'+data1.adl1);
      //use $ sign to split between address variables
      var add= data1.adl1+"$"+  data1.adl2 +"$"+ data1.city +"$"+ data1.state;
      console.log('Address is : '+add);

      $http({method: 'POST', url: '/ABC_Retail_Banking/deleteCust',data: { SSN_ID: data1.SSN_ID,customer_ID: data1.customer_ID, name: data1.name, age: data1.age, address: add }, cache: $templateCache}).
        then(function(response) {
          $scope.status = response.status;
          
          if($scope.status===200){
        	  $scope.cust.message='User Updated Succesfully with Customer_ID : '+response.data.Customer_ID;
        	  $scope.cust.su=true;
        	  $scope.cust.form1=false;
        	  $scope.cust.deleting=false;
        	  $scope.cust.deleteresult=false;
        	  $scope.cust.deletedSuccess=true;
        	  $scope.cust.deleteM="Customer Deleted Successfully";
        	  console.log('Customer_ID is : '+response.data.customer_ID);
        	  
          }
          $scope.data = response.data;
          $scope.cust.message1=response.data.message;
        }, function(response) {
          $scope.data = response.data || 'Request failed';
          $scope.status = response.status;
          $scope.cust.deleting=false;
          $scope.cust.failDeleting=true;
      });
    };
    
      
});
