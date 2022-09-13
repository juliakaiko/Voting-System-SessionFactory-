<%-- 
    Document   : candidate_form
    Created on : 24 июл. 2022 г., 19:15:46
    Author     : Dima
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="<c:url value='/static/css/myregistration_style.css' />" rel="stylesheet"></link>
    </head>
    <body>
        
        <div class="registration-form"> 
            <h2>Candidate Form</h2>
            <br>
            <form  method="POST"  class="form-horizontal ">     
                <div class="row justify-content-center">
                    <div class="form-group col-md-12"> 
                        <div class="col-md-7">
                            <%-- Элемент для ввода данных, path=> copy-paste из Entity --%>
                            <label class="col-md-2 control-lable" for="firstName">First Name</label>
                            <input type="text" path="firstName" name="firstName" id="firstName" placeholder="Enter candidate's First Name"  class="form-control input-sm"/>
                            <div class="has-error">
				<errors path="firstName" class="help-inline"/>
			    </div>   
                        </div>
                    </div>
                </div>             
                <div class="row justify-content-center">
                    <div class="form-group col-md-12">         
                        <div class="col-md-7">
                            <label class="col-md-2 control-lable" for="lastName">Last Name</label>
                            <input type="text" path="lastName" name="lastName" id="lastName" placeholder="Enter candidate's Last Name" class="form-control input-sm"/>
                             <div class="has-error">
				<errors path="lastName" class="help-inline"/>
			    </div>  
                        </div>
                    </div>
                </div>               
                 <div class="row justify-content-center">
                    <div class="form-group col-md-12">        
                        <div class="col-md-7">
                            <label class="col-md-2 control-lable" for="age">Age</label>
                            <input type="text" path="age" name="age" id="age" placeholder="Enter candidate's Age" class="form-control input-sm"/>
                             <div class="has-error">
				<errors path="age" class="help-inline"/>
			    </div>  
                        </div>
                    </div>
                </div> 
                <div class="row justify-content-center">
                    <div class="form-group col-md-12">        
                        <div class="col-md-7">
                            <label class="col-md-2 control-lable" for="voices">Voices</label>
                            <input type="text" path="voices" name="voices" id="voices" placeholder="Enter candidate's voices" class="form-control input-sm"/>
                             <div class="has-error">
				<errors path="voices" class="help-inline"/>
			    </div>  
                        </div>
                    </div>
                </div> 
                <!--</form><!-- comment
                 <form  method="POST"  class="form-horizontal ">  -->            
                 <div class="row justify-content-center">
                    <div class="form-group col-md-12">                              
                        <div class="col-md-7">
                            <label class="col-md-3 control-lable" for="education">Education</label>
                            <input type="text" path="education" name ="education" id="education" placeholder="Enter candidate's Education" class="form-control input-sm"/>
                            <div class="has-error">
				<errors path="education" class="help-inline"/>
			    </div>  
                        </div>
                    </div>
                </div>                                                                           
                <div class="row justify-content-center">
                    <div class="form-group col-md-12">         
                        <div class="col-md-7">
                            <label class="col-md-3 control-lable" for="annualIncome">Annual Income</label>
                            <input type="text" path="annualIncome" name="annualIncome" id="annualIncome" placeholder="Enter candidate's annual income" class="form-control input-sm"/>
                             <div class="has-error">
				<errors path="annualIncome" class="help-inline"/>
			    </div>  
                        </div>
                    </div>
                </div> 
               <div class="row justify-content-center">
                    <div class="form-group col-md-12">         
                        <div class="col-md-7">
                            <label class="col-md-3 control-lable" for="property">Property</label>
                            <input type="text" path="property" name="property" id="property" placeholder="Enter candidate's property" class="form-control input-sm"/>
                             <div class="has-error">
				<errors path="property" class="help-inline"/>
			    </div>  
                        </div>
                    </div>
                </div> 
               <div class="row justify-content-center">
                    <div class="form-group col-md-12">         
                        <div class="col-md-7">
                            <label class="col-md-3 control-lable" for="electionPromises">Election Promises</label>
                            <input type="text" path="electionPromises" name="electionPromises" id="electionPromises" placeholder="Enter candidate's election promises" class="form-control input-sm"/>
                             <div class="has-error">
				<errors path="electionPromises" class="help-inline"/>
			    </div>  
                        </div>
                    </div>
                </div>            
               
                <div class="row justify-content-end ">
                    <div class="form-actions floatRight">  
                        <input type="submit" value ="Save" class="btn btn-white btn-animate" /> 
                    </div>
                </div>                 
            </form>
             
        </div> 

    </body>
</html>
