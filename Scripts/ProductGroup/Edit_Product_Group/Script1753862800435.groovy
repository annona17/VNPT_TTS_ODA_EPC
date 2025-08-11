import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.setText(findTestObject('Edit_Product_Group/Page_Root Config/input__name'), name.toString())

WebUI.setText(findTestObject('Edit_Product_Group/Page_Root Config/input__code'), code.toString())

WebUI.setText(findTestObject('Edit_Product_Group/Page_Root Config/textarea_Description_description'), description.toString())

TestObject startDateField = findTestObject('Object Repository/Edit_Product_Group/Page_Root Config/input_ValidFor_startDate')
TestObject endDateField = findTestObject('Object Repository/Edit_Product_Group/Page_Root Config/input_ValidFor_endDate')

// Xóa trước rồi gán giá trị ngày mới cho startDate
WebUI.executeJavaScript(
	"arguments[0].value=''; arguments[0].dispatchEvent(new Event('input'));" +
	"arguments[0].value='" + startDate.toString() + "'; arguments[0].dispatchEvent(new Event('change'))",
	Arrays.asList(WebUI.findWebElement(startDateField))
)

// Tương tự cho endDate
WebUI.executeJavaScript(
	"arguments[0].value=''; arguments[0].dispatchEvent(new Event('input'));" +
	"arguments[0].value='" + endDate.toString() + "'; arguments[0].dispatchEvent(new Event('change'))",
	Arrays.asList(WebUI.findWebElement(endDateField))
)

if (!status.toString().blank) {
	WebUI.click(findTestObject("Object Repository/Edit_Product_Group/Page_Root Config/div_Status_combobox"))
	String valueToSelect = status.toString()
	TestObject option = findTestObject('Object Repository/Edit_Product_Group/Page_Root Config/div_Status_option', [('option'): valueToSelect])
	WebUI.waitForElementPresent(option, 5)
	WebUI.click(option)
}

WebUI.setText(findTestObject('Edit_Product_Group/Page_Root Config/input_Version_version'), version.toString())

