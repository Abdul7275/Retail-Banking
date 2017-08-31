CApp.controller("GetStatement", function ($scope, $http, $templateCache) {
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
     
$scope.dep.showAccount = function(){
	$scope.dep.customer=false;
	$scope.dep.account=true;
};
$scope.dep.showCustomer = function(){
	$scope.dep.customer=true;
	$scope.dep.account=false;
};


     //alert("deposit controller working");
     console.log('inside getstatement controller working');
   //write function for validating then run function only when validations are correct
     $scope.dep.submitForm = function() {
    	 var data3=$scope.dep;
    	 console.log('inside deposit controller working');
         // check to make sure the form is completely valid
    	 //everything is correct then run function from it
    	 //check if customer ID is coming or account ID
         if ($scope.dep.account) {
             
           console.log('account is choosen');
           //initialize array to 0 before making again request
           $scope.dep.getaccounts=[];
             //run create Customer function
             $scope.dep.getStatementByTransactionNo();
             //alert('no of transaction : '+data3.trno);
             console.log('no of transaction '+ data3.trno);
         }
         else if ($scope.dep.customer) {
             
             console.log('customer is choosen');
           //initialize array to 0 before making again request
             $scope.dep.getaccounts=[];
            // alert('start date : '+data3.startdate);
             console.log('start date '+ data3.startdate);
             console.log('end date '+ data3.enddate);
               //run create Customer function
             $scope.dep.getStatementByDate();
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

        $http({method: 'POST', url: '/ABC_Retail_Banking/showaccounts',data: { customer_ID: data1.customer_ID}, cache: $templateCache}).
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
      $scope.dep.getStatementByTransactionNo = function() {
          $scope.dep.depositing=true;
          $scope.dep.form1=true;
          $scope.code = null;
          $scope.response = null;
          var data1=$scope.dep;
          console.log('Account ID :'+data1.account_ID);
          console.log('No of transactions  :'+data1.trno);

          $http({method: 'POST', url: '/ABC_Retail_Banking/get_tran',data: { no_of_transaction: data1.trno,account_ID:data1.account_ID}, cache: $templateCache}).
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
        $scope.dep.getStatementByDate = function() {
            $scope.dep.depositing=true;
            $scope.dep.form1=true;
            $scope.code = null;
            $scope.response = null;
            var data1=$scope.dep;
            console.log('Account ID :'+data1.account_ID);
            console.log('No of transactions  :'+data1.trno);

            $http({method: 'POST', url: '/ABC_Retail_Banking/stmt_date',data: { start_date: data1.startdate,end_date:data1.enddate, account_ID:data1.account_ID}, cache: $templateCache}).
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
