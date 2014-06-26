/*
 * Constants.java Created on April 3, 2006, 1:05 PM
 */

package com.sb.common.util;

public class Constants
	{

		public final static String ACCOUNT_DETAIL_VIEW_ACCNO = "AccountdetailViewAccNo";
		public final static String ACCOUNT_DETAIL_VIEW_JOBID = "AccountdetailViewJobId";
		public final static String ACCOUNT_DETAIL_VIEW_HISTORYID = "AccountdetailViewHistoryId";
		public final static String ACCOUNT_NO_KEY = "AccountNo";
		public final static String UNIQUE_PIN_KEY = "UniquePIN";
		/*
		 * Cache related constants...here
		 */
		public final static long CACHE_ITEMS_EXPIRYTIME = 7200000; // 2 hour
		public final static String GROUPCHAT_SKILLSETTYPEID = "b0fdad11-0bf5-457b-804a-2914d546d8c2";
		// public final static String
		// GROUPCHAT_SKILLSETTYPEID="84e75d87-1c21-4268-94c8-520c687f3c3e";

		/*
		 * manage message related constants...here
		 */
		public final static int DISPLAY_MESSAGE_LIST_PAGESIZE = 27;
		public final static int ENTER_KEY_VALUE = 13;

		// contactmethodtype id related constant

		public final static String EMAIL_METHOD_TYPE_ID = "e8f41ae1-4ff9-40c4-9a35-3c962952e08a";
		public final static String FAX_METHOD_TYPE_ID = "032159f1-bb48-461d-9c80-46185b587be0";
		public final static String TEXT__METHOD_TYPE_ID = "a5a88063-76e8-495e-80fe-0d3308fd43ef";
		public final static String ATTACHMENT__METHOD_TYPE_ID = "f720667a-ec7e-48df-b433-132d461bd853";
		public final static String TEXTMAIL__METHOD_TYPE_ID = "e21aa193-0d15-4245-abaf-3c85739b85f6";
		public final static String AGENT_FEEDBACK_EMAIL_METHOD_TYPE_ID = "4a32fe58-1173-47fd-83fd-a60bd394b093";
		public final static String CSV_ATTACHMENT__METHOD_TYPE_ID = "29ef8ff8-caa9-4c33-85d5-b8e356162433";

		public final static String MESSAGE_STEP_ID = "27490529-ecd4-45a7-9f87-6d83ad1eb08e";

		// message form status::
		public final static String MESSAGE_FORM_OPERATION_FAILED_CHARACTER = "There is some special character in the message form can u check it";
		public final static String MESSAGE_FORM_OPERATION_FAILED_IN_MSGFR = "There is some problem in building the message form";
		public final static String MESSAGE_FORM_OPERATION_FAILED = "Message Form Operation Failed";
		public final static String MESSAGE_FORM_SAVED_SUCCESS = "Message Form Save Successfully";
		public final static String MESSAGE_FORM_SAVED_FAILED = "Message Save or Send operation is failed please try again";
		// public final static String MESSAGE_FORM_SAVED_FAILED =
		// "Message Form Save Failed";
		public final static String MESSAGE_FORM_SEND_SUCCESS = "Message Send Successfully";
		public final static String MESSAGE_FORM_SEND_FAILED = "Message was failed while Sending mail contact development or IT team";
		// public final static String MESSAGE_FORM_SEND_FAILED =
		// "Message Send Failure";
		public final static String MESSAGE_FORM_SAVE_N_SEND_SUCCESS = "Message Saved and Send Succesfully";
		// public final static String MESSAGE_FORM_SAVE_N_SEND_FAILED =
		// "Message Save and Send Failed";
		public final static String MESSAGE_FORM_SAVE_N_SEND_FAILED = "Message is Save, but it is failed to send msg to client";
		public final static String MESSAGE_FORM_UPDATE_SUCCESS = "Message Update Succesfully";
		public final static String MESSAGE_FORM_UPDATE_FAILED = "Message update got failed, please try again";
		public final static String MESSAGE_FORM_SEND_SUCCESS_UPDATE_FAILED = "Message Send Succesfully But Error in Update History";
		public final static String MESSAGE_FORM_NOTHING_TO_UPDATE = "Nothing To Update";
		public final static String MESSAGE_FORM_ANNOTATE_SUCCESS = "Annotate Message Save Succesfully";
		public final static String MESSAGE_FORM_ANNOTATE_FAILED = "Message annotation is got failed, please try again";

		public final static String MESSAGE_FORM_HISTORY_LIST_COMPLETE = "history complete";
		public final static String MESSAGE_FORM_SUBHISTORY_LIST_COMPLETE = "sub history complete";
		public final static String MESSAGE_FORM_HISTORY_DELAIL_LIST_COMPLETE = "history detail complete";
		public final static String MESSAGE_FORM_MARK_DELIVERED_SUCCESS = "Marked Delivered Successflly";
		public final static String MESSAGE_FORM_MARK_DELIVERED_FAILED = "Marking Problem";
		public final static String MESSAGE_FORM_MARK_UNDELIVERED_SUCCESS = "Marked Undelivered Successflly";
		public final static String MESSAGE_FORM_MARK_UNDELIVERED_FAILED = "Marking Problem";
		public final static String MESSAGE_FORM_ANNOTATION_UPDATE_SUCCESS = "Annotation Update Successfully";
		public final static String MESSAGE_FORM_CALLCONCLUSION_SUCCESS = "CallConclusion History Save Successflly";
		public final static String MESSAGE_FORM_CALLCONCLUSION_FAILED = "CallConclusion History Save Problem";
		public final static String MESSAGE_FORM_SHOW_UPDATE_WINDOW = "message form update window appear";
		public final static String MESSAGE_FORM_SHOW_SEND_WINDOW = "message form send window appear";
		public final static String MESSAGE_FORM_SHOW_ANNOTATE_WINDOW = "message form annotate window appear";
		public final static String REPEAT_SUCCCESSFUL = "Repeat Saved Successfully";
		public final static String REPEAT_FAILED = "Repeat Failed";
		// error messages
		public final static String HISTORY_RETRIVAL_OPERATION_FAILED = "Problem occurred while retrieving the history.";
		public final static String SUBHISTORY_RETRIVAL_OPERATION_FAILED = "Problem occurred while retrieving the sub history details.";
		public final static String HISTORYDETAIL_RETRIVAL_OPERATION_FAILED = "Problem occurred while retrieving the history details.";

		// Cache Keys
		public final static String Cache_Key_For_ProductName = "ProCode";
		public final static String Cache_Key_For_ProductId = "ProId";
	}
