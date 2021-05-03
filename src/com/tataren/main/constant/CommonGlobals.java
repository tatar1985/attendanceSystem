package com.tataren.main.constant;

import java.util.Locale;
import java.util.ResourceBundle;

import com.tataren.main.util.StringUtils;


public class CommonGlobals {

	public static final String UPLOAD_FOLDER;

	public static final String TEMPLATE_DIR;

	public static final String SAP_INTERFACE_INFO_IS_DELETED = "L";

	public static final String INVOICE_AMT_DIFF;

	public static final String INVOICE_RATE_DIFF;;

	public static final String[] Indirect_Purchase_Organize_Jq;

	public static final String[] Indirect_Purchase_Organize_Patac;

	public static final String[] Indirect_Purchase_Organize_Norsom;

	public static final String[] Indirect_Purchase_Organize_Dy;

	public static final String[] Indirect_Purchase_Organize_Dypt;

	public static final boolean sortIndicatorUseImage = false;

	public static final String sortIndicatorImageAsc = "<font color=\"red\">¡ü</font>";

	public static final String sortIndicatorImageDesc = "<font color=\"yellow\">¡ý</font>";

	public static final String sortIndicatorImageNon = "";

	public static final String sortIndicatorCharAsc = "<font color=\"red\">¡ü</font>";

	public static final String sortIndicatorCharDesc = "<font color=\"yellow\">¡ý</font>";

	public static final String sortIndicatorCharNon = "";

	public static final int invoicePrecheckMaxFileSize;

	public static final int ppasPreCheckMaxLines;

	public static final int indirectPreCheckMaxLines;

	public static String timerStartDate;

	public static int timerStartDate_Hour;

	public static int timerStartDate_Minute;

	public static int timerStartDate_Second;

	public static int timerStartDate_AM_PM;

	public static String timerCycle;

	public static boolean moftecMailPowerSwitch;

	public static boolean is_Manual_Confirm_SAP_Reject_Invoice;

	static {
		ResourceBundle res = ResourceBundle.getBundle("conf.conf");

		// sortIndicatorImageAsc = res.getString("sort.indicator.image.asc");
		// sortIndicatorImageDesc = res.getString("sort.indicator.image.desc");
		// sortIndicatorImageNon = res.getString("sort.indicator.image.non");
		//		
		// sortIndicatorCharAsc = res.getString("sort.indicator.char.asc");
		// sortIndicatorCharDesc = res.getString("sort.indicator.char.desc");
		// sortIndicatorCharNon = res.getString("sort.indicator.char.non");

		String temp;

		UPLOAD_FOLDER = res.getString("common.uploadfolder");
		INVOICE_AMT_DIFF = res.getString("invoice.amt.diff");
		INVOICE_RATE_DIFF = res.getString("invoice.rate.diff");
		timerStartDate = res.getString("timer.startDate");
		try {
			timerStartDate_Hour = Integer.parseInt(res.getString(
					"timer.startDate.hour").trim());

			timerStartDate_Minute = Integer.parseInt(res.getString(
					"timer.startDate.minute").trim());

			timerStartDate_Second = Integer.parseInt(res.getString(
					"timer.startDate.second").trim());

			timerStartDate_AM_PM = Integer.parseInt(res
					.getString("timer.startDate.am_pm"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		timerCycle = res.getString("timer.cycle").trim();

		temp = res.getString("mail.moftec.power").trim();
		moftecMailPowerSwitch = Boolean.valueOf(temp).booleanValue();

		temp = res.getString("timer.invoice.manual.confirm").trim();
		is_Manual_Confirm_SAP_Reject_Invoice = Boolean.valueOf(temp)
				.booleanValue();

		String indirect;

		indirect = res.getString("po.indirect.9B60");
		Indirect_Purchase_Organize_Jq = StringUtils.split(indirect, ",");

		indirect = res.getString("po.indirect.9B80");
		Indirect_Purchase_Organize_Patac = StringUtils.split(indirect, ",");

		indirect = res.getString("po.indirect.9150");
		Indirect_Purchase_Organize_Norsom = StringUtils.split(indirect, ",");

		indirect = res.getString("po.indirect.9G60");
		Indirect_Purchase_Organize_Dy = StringUtils.split(indirect, ",");

		indirect = res.getString("po.indirect.9G80");
		Indirect_Purchase_Organize_Dypt = StringUtils.split(indirect, ",");

		TEMPLATE_DIR = res.getString("report.template.base.dir");

		temp = res.getString("invoice.precheck.maxfilesize").trim();
		invoicePrecheckMaxFileSize = Integer.parseInt(temp);

		temp = res.getString("invoice.precheck.ppas.maxlines").trim();
		ppasPreCheckMaxLines = Integer.parseInt(temp);

		temp = res.getString("invoice.precheck.indirect.maxlines").trim();
		indirectPreCheckMaxLines = Integer.parseInt(temp);

	}

	public static void main(String[] rgs) {
		System.out.println(CommonGlobals.TEMPLATE_DIR);
	}

}
