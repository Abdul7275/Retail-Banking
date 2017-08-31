mainApp.controller("ViewAccount", function ($scope, $http, $templateCache) {
     $scope.dep={};
     $scope.dep.depSuccess=false;
     $scope.dep.successResult=false;
     $scope.dep.depform=true;
     $scope.dep.depositing=false;
     $scope.dep.prev_B=0;
     $scope.dep.cr_B=0;
     $scope.dep.getaccounts=[];
     $scope.dep.account_ID="0";
     $scope.dep.customer_ID="1";
     



     //alert("deposit controller working");
     console.log('inside deposit controller working');
   //write function for validating then run function only when validations are correct
     $scope.dep.submitForm = function() {
    	 var data3=$scope.dep;
    	 console.log('inside deposit controller working');
         // check to make sure the form is completely valid
    	 //everything is correct then run function from it
    	 //check if customer ID is coming or account ID
         
          if ($scope.dep.account_ID!=null) {
             
             console.log('customer is choosen');
           //initialize array to 0 before making again request
             $scope.dep.getaccounts=[];
             //alert('customer id : '+data3.customer_ID);
               //run create Customer function
               $scope.dep.getAccount();
           }

     };
     
     
      $scope.dep.deposit = function() {
      $scope.dep.depositing=true;
      $scope.dep.form1=true;
      $scope.code = null;
      $scope.response = null;
      var data1=$scope.dep;
      console.log('Account ID :'+data1.account_ID);
      console.log('Amount :'+data1.customer_ID);

      $http({method: 'POST', url: '/ABC_Retail_Banking/deposit',data: { account_ID: data1.account_ID, amount: data1.amount }, cache: $templateCache}).
        then(function(response) {
          $scope.status = response.status;
          if($scope.status===200){
        	  $scope.dep.message=response.data.message;
        	  $scope.dep.prev_B=response.data.prev_B;
        	  $scope.dep.cr_B=response.data.cr_B;
        	  $scope.dep.account_ID=response.data.account_ID;
        	  $scope.dep.customer_ID=response.data.customer_ID;
        	  $scope.dep.message=response.data.message;
        	  $scope.dep.depSuccess=true;
        	  if(response.data.err==="false"){
        		  $scope.dep.successResult=true;
        	  }
        	  
        	  $scope.dep.depform=false;
        	  $scope.dep.depositing=false;
        
        	  
          }
          $scope.data = response.data;
          $scope.dep.message1=response.data.message;
        }, function(response) {
          $scope.data = response.data || 'Request failed';
          $scope.status = response.status;
          $scope.dep.depositing=false;
          $scope.dep.message="error on server";
      });
    };
    $scope.dep.getAccounts = function() {
        $scope.dep.depositing=true;
        $scope.dep.form1=true;
        $scope.code = null;
        $scope.response = null;
        var data1=$scope.dep;
        console.log('Account ID :'+data1.account_ID);
        console.log('Amount :'+data1.customer_ID);

        $http({method: 'POST', url: '/ABC_Retail_Banking/showcustomer',data: { customer_ID: data1.customer_ID}, cache: $templateCache}).
          then(function(response) {
            $scope.status = response.status;
            if($scope.status===200){
          	  $scope.dep.message=response.data.message;
          	  $scope.dep.prev_B=response.data.prev_B;
          	  $scope.dep.cr_B=response.data.cr_B;
          	  $scope.dep.account_ID=response.data.account_ID;
          	  $scope.dep.customer_ID=response.data.customer_ID;
          	  $scope.dep.message=response.data.message;
          	  $scope.dep.depSuccess=true;
          	  if(response.data.err==="false"){
          		  $scope.dep.successResult=true;
          	  }
          	  else if(response.data.length===0){
                $scope.dep.noData=true;
             
            }
            //loading 5 articles at a time.
            for (var i = 0; i < response.data.length; i++) {
             $scope.dep.getaccounts.push(response.data[i]);
             $scope.dep.showdata=true;
          }
            console.log("getaccounts length :"+ $scope.dep.getaccounts.length);
          	  
          	  $scope.dep.depform=false;
          	  $scope.dep.depositing=false;
          
          	  
            }
            $scope.data = response.data;
            $scope.dep.message1=response.data.message;
          }, function(response) {
            $scope.data = response.data || 'Request failed';
            $scope.status = response.status;
            $scope.dep.depositing=false;
            $scope.dep.message="error on server";
        });
      };
      $scope.dep.getAccount = function() {
          $scope.dep.depositing=true;
          $scope.dep.form1=true;
          $scope.code = null;
          $scope.response = null;
          var data1=$scope.dep;
          console.log('Account ID :'+data1.account_ID);
          console.log('Amount :'+data1.customer_ID);

          $http({method: 'POST', url: '/ABC_Retail_Banking/showaccountex',data: { account_ID: data1.account_ID}, cache: $templateCache}).
            then(function(response) {
              $scope.status = response.status;
              if($scope.status===200){
            	  $scope.dep.message=response.data.message;
            	  $scope.dep.prev_B=response.data.prev_B;
            	  $scope.dep.cr_B=response.data.cr_B;
            	  $scope.dep.account_ID=response.data.account_ID;
            	  $scope.dep.customer_ID=response.data.customer_ID;
            	  $scope.dep.message=response.data.message;
            	  $scope.dep.depSuccess=true;
            	  if(response.data.err==="false"){
            		  $scope.dep.successResult=true;
            	  }
            	  else if(response.data.length===0){
                  $scope.dep.noData=true;
               
              }
              //loading 5 articles at a time.
              for (var i = 0; i < response.data.length; i++) {
               $scope.dep.getaccounts.push(response.data[i]);
               $scope.dep.showdata=true;
            }
              console.log("getaccounts length :"+ $scope.dep.getaccounts.length);
            	  
            	  $scope.dep.depform=false;
            	  $scope.dep.depositing=false;
            
            	  
              }
              $scope.data = response.data;
              $scope.dep.message1=response.data.message;
            }, function(response) {
              $scope.data = response.data || 'Request failed';
              $scope.status = response.status;
              $scope.dep.depositing=false;
              $scope.dep.message="error on server";
          });
        };
      
});
