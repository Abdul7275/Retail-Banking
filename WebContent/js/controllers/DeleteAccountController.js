mainApp.controller("DeleteAccountController", function ($scope, $http, $templateCache) {
     $scope.acc={"customer_ID":"","account_Type":"","account":""};
     
     $scope.acc.fetchresult=true;
     $scope.acc.deleteresult=false;
     $scope.acc.fetching=false;
     $scope.acc.deleting=false;
     $scope.acc.failFetching=false;
     $scope.acc.failDeleting=false;
     $scope.acc.noRecord=false;
     $scope.acc.deletedSuccess=false;
     // function for fetching results starts here
     $scope.acc.fetchResult = function() {
         $scope.acc.fetching=true;
         $scope.acc.form1=true;
         $scope.code = null;
         $scope.response = null;
         $scope.acc.su=false;
         var data1=$scope.acc;
         console.log('Account Type :'+data1.account_Type);
         console.log('Customer ID :'+data1.customer_ID);
         console.log('Account ID :'+data1.account_ID);
       // create if statement which checks for validations
         // check that customer_ID and Account Type Both are inputed or
         // Account id is inpited then only request to server
         // otherwise alert message
     
         $http({method: 'POST', url: '/ABC_Retail_Banking/getAcc',data: { account_Type: data1.account_Type,customer_ID:data1.customer_ID,account_ID:data1.account_ID }, cache: $templateCache}).
           then(function(response) {
             $scope.status = response.status;
             console.log('value of err is :'+response.data.err);
             if((response.data.err==='true')){
            	 $scope.acc.noRecord=true;
            	 $scope.acc.fetching=false;
             }
             if(($scope.status===200) &&(response.data.err==='false')){
           	  $scope.acc.message='Customer data Succesfully fetched Customer_ID : '+response.data.Customer_ID;
           
           	  $scope.acc.fetching=false;
           	  $scope.acc.fetchresult=false;
           	$scope.acc.account_ID=response.data.account_ID;
           	$scope.acc.customer_ID=response.data.customer_ID;
           	$scope.acc.account_Type=response.data.account_Type;
           	$scope.acc.balance=response.data.balance;
           	$scope.acc.date=response.data.date;
           
           	  $scope.acc.deleteresult=true;
           	  console.log('Customer_ID after fetch result is  : '+response.data.customer_ID);
           	console.log('message after fetch result is  : '+response.data.message);
             }
             $scope.data = response.data;
             $scope.acc.message1=response.data.message;
           }, function(response) {
             $scope.data = response.data || 'Request failed';
             $scope.status = response.status;
             $scope.acc.failFetching=true;
             $scope.acc.fetching=false;
         });
       };
    // function for fetching results ends here
     
      $scope.acc.deleteAccount = function() {
      $scope.acc.deleting=true;

      $scope.code = null;
      $scope.response = null;
     
      var data1=$scope.acc;
      console.log('Account Type :'+data1.account_Type);
      console.log('customer_ID :'+data1.customer_ID);
      console.log('Account ID :'+data1.account_ID);
      console.log('Balance :'+data1.balance);
      console.log('Date is :'+data1.date);
      //use $ sign to split between address variables


      $http({method: 'POST', url: '/ABC_Retail_Banking/deleteAcc',data: { account_Type: data1.account_Type,customer_ID: data1.customer_ID, account_ID: data1.account_ID, balance: data1.balance}, cache: $templateCache}).
        then(function(response) {
          $scope.status = response.status;
          
          if($scope.status===200){
        	  $scope.acc.message='User Updated Succesfully with Customer_ID : '+response.data.customer_ID;
        
        	  $scope.acc.deleting=false;
        	  $scope.acc.deleteresult=false;
        	  $scope.acc.deletedSuccess=true;
        	  $scope.acc.deleteM="Account Deleted Successfully";
        	  
        	  
          }
          $scope.data = response.data;
          $scope.acc.message1=response.data.message;
        }, function(response) {
          $scope.data = response.data || 'Request failed';
          $scope.status = response.status;
          $scope.acc.deleting=false;
          $scope.acc.failDeleting=true;
      });
    };
    
      
});
