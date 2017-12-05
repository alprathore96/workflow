package com.testing.controllers;

import com.testing.services.HomeService;
import com.testing.workflow.exceptions.InvalidMethodArgumentException;
import com.testing.workflow.exceptions.InvalidOperationPathException;
import com.testing.workflow.manager.WorkflowFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/home")
public class HomeController {

    @Autowired
    HomeService homeService;
    @Autowired
    WorkflowFactory workflowFactory;

    @RequestMapping
    public String home() {
        return "home";
    }

    @ResponseBody
    @RequestMapping(value = "/get")
    public String get() {
        return homeService.get();
    }

    @ResponseBody
    @RequestMapping(value = "/trigger/{workflow_id}")
    public String trigger(@ModelAttribute("requestId") String requestId, @PathVariable("workflow_id") String workflow_id) throws InvalidMethodArgumentException, InvalidOperationPathException {
        Map<String, Object> workflowAttributes = new HashMap<>();
        workflowAttributes.put("hotelId", "1");
        workflowAttributes.put("another", "some value");
        workflowFactory.triggerWorkflow(workflow_id, requestId.toString(), workflowAttributes);
        return "triggered";
    }

    @ResponseBody
    @RequestMapping(value = "/available", method = RequestMethod.POST)
    public String available(@RequestBody String xml) {
        return "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                   xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "    <SOAP-ENV:Body>\n" +
                "        <MultiAvailabilityResponse Status=\"Successful\" Token=\"187be58c62c2f2515b5d78ee\">\n" +
                "            <Availabilities>\n" +
                "                <Availability CurrencyCode=\"INR\" HotelCode=\"IHG-BOMAP\">\n" +
                "                    <GuestCount AdultCount=\"1\" ChildCount=\"2\" ChildAges=\"8,10\"/>\n" +
                "                    <RoomTypes>\n" +
                "                        <RoomType RoomTypeCode=\"KDXN\" RoomTypeName=\"1 KING SUPERIOR NONSMOKING\">\n" +
                "                            <RoomTypeDescription>1 KING SUPERIOR NONSMOKING</RoomTypeDescription>\n" +
                "                        </RoomType>\n" +
                "                        <RoomType RoomTypeCode=\"CDXN\" RoomTypeName=\"DELUXE ROOM\">\n" +
                "                            <RoomTypeDescription>DELUXE ROOM</RoomTypeDescription>\n" +
                "                        </RoomType>\n" +
                "                    </RoomTypes>\n" +
                "                    <RatePlans>\n" +
                "                        <RatePlan RatePlanCode=\"IDH15\" RatePlanName=\"IDH15\">\n" +
                "                            <CancelPolicy NonRefundable=\"true\">\n" +
                "                                <CancelPenalties/>\n" +
                "                            </CancelPolicy>\n" +
                "                        </RatePlan>\n" +
                "                        <RatePlan RatePlanCode=\"IDU15\" RatePlanName=\"IDU15\">\n" +
                "                            <CancelPolicy NonRefundable=\"false\">\n" +
                "                                <CancelPenalties>\n" +
                "                                    <CancelPenalty MaxHoursBeforeCheckIn=\"48\" MinHoursBeforeCheckIn=\"0\" Nights=\"1\"\n" +
                "                                                   NoShow=\"false\"/>\n" +
                "                                    <CancelPenalty Nights=\"1\" NoShow=\"true\"/>\n" +
                "                                </CancelPenalties>\n" +
                "                            </CancelPolicy>\n" +
                "                        </RatePlan>\n" +
                "                    </RatePlans>\n" +
                "                    <RoomRates>\n" +
                "                        <RoomRate MealPlanCode=\"RO\" RatePlanCode=\"IDH15\" RoomTypeCode=\"KDXN\">\n" +
                "                            <Rates>\n" +
                "                                <Rate AmountAfterTax=\"8453.760\" AmountBeforeTax=\"7140.000\" EffectiveDate=\"2015-10-11\"\n" +
                "                                      ExpireDate=\"2015-10-12\"/>\n" +
                "                                <Rate AmountAfterTax=\"8982.120\" AmountBeforeTax=\"7586.250\" EffectiveDate=\"2015-10-12\"\n" +
                "                                      ExpireDate=\"2015-10-16\"/>\n" +
                "                                <Rate AmountAfterTax=\"6843.520\" AmountBeforeTax=\"5780.000\" EffectiveDate=\"2015-10-16\"\n" +
                "                                      ExpireDate=\"2015-10-17\"/>\n" +
                "                            </Rates>\n" +
                "                            <Fees>\n" +
                "                                <Fee ChargeType=\"Tax\" Percent=\"18.400\" Type=\"Exclusive\"/>\n" +
                "                            </Fees>\n" +
                "                        </RoomRate>\n" +
                "                        <RoomRate MealPlanCode=\"RO\" RatePlanCode=\"IDU15\" RoomTypeCode=\"CDXN\">\n" +
                "                            <Rates>\n" +
                "                                <Rate AmountAfterTax=\"12580.000\" AmountBeforeTax=\"10625.000\" EffectiveDate=\"2015-10-11\"\n" +
                "                                      ExpireDate=\"2015-10-16\"/>\n" +
                "                                <Rate AmountAfterTax=\"10567.200\" AmountBeforeTax=\"8925.000\" EffectiveDate=\"2015-10-16\"\n" +
                "                                      ExpireDate=\"2015-10-17\"/>\n" +
                "                            </Rates>\n" +
                "                            <Fees>\n" +
                "                                <Fee ChargeType=\"Tax\" Percent=\"18.400\" Type=\"Exclusive\"/>\n" +
                "                            </Fees>\n" +
                "                        </RoomRate>\n" +
                "                        <RoomRate MealPlanCode=\"RO\" RatePlanCode=\"IDH15\" RoomTypeCode=\"CDXN\">\n" +
                "                            <Rates>\n" +
                "                                <Rate AmountAfterTax=\"10064.000\" AmountBeforeTax=\"8500.000\" EffectiveDate=\"2015-10-11\"\n" +
                "                                      ExpireDate=\"2015-10-12\"/>\n" +
                "                                <Rate AmountAfterTax=\"10693.000\" AmountBeforeTax=\"9031.250\" EffectiveDate=\"2015-10-12\"\n" +
                "                                      ExpireDate=\"2015-10-16\"/>\n" +
                "                                <Rate AmountAfterTax=\"8453.760\" AmountBeforeTax=\"7140.000\" EffectiveDate=\"2015-10-16\"\n" +
                "                                      ExpireDate=\"2015-10-17\"/>\n" +
                "                            </Rates>\n" +
                "                            <Fees>\n" +
                "                                <Fee ChargeType=\"Tax\" Percent=\"18.400\" Type=\"Exclusive\"/>\n" +
                "                            </Fees>\n" +
                "                        </RoomRate>\n" +
                "                    </RoomRates>\n" +
                "                </Availability>\n" +
                "                <Availability CurrencyCode=\"INR\" HotelCode=\"IHG-BOMAP\">\n" +
                "                    <GuestCount AdultCount=\"2\" ChildCount=\"1\" ChildAges=\"11\"/>\n" +
                "                    <RoomTypes>\n" +
                "                        <RoomType RoomTypeCode=\"KDXN\" RoomTypeName=\"1 KING SUPERIOR NONSMOKING\">\n" +
                "                            <RoomTypeDescription>1 KING SUPERIOR NONSMOKING</RoomTypeDescription>\n" +
                "                        </RoomType>\n" +
                "                        <RoomType RoomTypeCode=\"CDXN\" RoomTypeName=\"DELUXE ROOM\">\n" +
                "                            <RoomTypeDescription>DELUXE ROOM</RoomTypeDescription>\n" +
                "                        </RoomType>\n" +
                "                    </RoomTypes>\n" +
                "                    <RatePlans>\n" +
                "                        <RatePlan RatePlanCode=\"IDH15\" RatePlanName=\"IDH15\">\n" +
                "                            <CancelPolicy NonRefundable=\"true\">\n" +
                "                                <CancelPenalties/>\n" +
                "                            </CancelPolicy>\n" +
                "                        </RatePlan>\n" +
                "                        <RatePlan RatePlanCode=\"IDU15\" RatePlanName=\"IDU15\">\n" +
                "                            <CancelPolicy NonRefundable=\"false\">\n" +
                "                                <CancelPenalties>\n" +
                "                                    <CancelPenalty MaxHoursBeforeCheckIn=\"48\" MinHoursBeforeCheckIn=\"0\" Nights=\"1\"\n" +
                "                                                   NoShow=\"false\"/>\n" +
                "                                    <CancelPenalty Nights=\"1\" NoShow=\"true\"/>\n" +
                "                                </CancelPenalties>\n" +
                "                            </CancelPolicy>\n" +
                "                        </RatePlan>\n" +
                "                    </RatePlans>\n" +
                "                    <RoomRates>\n" +
                "                        <RoomRate MealPlanCode=\"RO\" RatePlanCode=\"IDH15\" RoomTypeCode=\"KDXN\">\n" +
                "                            <Rates>\n" +
                "                                <Rate AmountAfterTax=\"8453.760\" AmountBeforeTax=\"7140.000\" EffectiveDate=\"2015-10-11\"\n" +
                "                                      ExpireDate=\"2015-10-12\"/>\n" +
                "                                <Rate AmountAfterTax=\"8982.120\" AmountBeforeTax=\"7586.250\" EffectiveDate=\"2015-10-12\"\n" +
                "                                      ExpireDate=\"2015-10-16\"/>\n" +
                "                                <Rate AmountAfterTax=\"6843.520\" AmountBeforeTax=\"5780.000\" EffectiveDate=\"2015-10-16\"\n" +
                "                                      ExpireDate=\"2015-10-17\"/>\n" +
                "                            </Rates>\n" +
                "                            <Fees>\n" +
                "                                <Fee ChargeType=\"Tax\" Percent=\"18.400\" Type=\"Exclusive\"/>\n" +
                "                            </Fees>\n" +
                "                        </RoomRate>\n" +
                "                        <RoomRate MealPlanCode=\"RO\" RatePlanCode=\"IDU15\" RoomTypeCode=\"CDXN\">\n" +
                "                            <Rates>\n" +
                "                                <Rate AmountAfterTax=\"12580.000\" AmountBeforeTax=\"10625.000\" EffectiveDate=\"2015-10-11\"\n" +
                "                                      ExpireDate=\"2015-10-16\"/>\n" +
                "                                <Rate AmountAfterTax=\"10567.200\" AmountBeforeTax=\"8925.000\" EffectiveDate=\"2015-10-16\"\n" +
                "                                      ExpireDate=\"2015-10-17\"/>\n" +
                "                            </Rates>\n" +
                "                            <Fees>\n" +
                "                                <Fee ChargeType=\"Tax\" Percent=\"18.400\" Type=\"Exclusive\"/>\n" +
                "                            </Fees>\n" +
                "                        </RoomRate>\n" +
                "                        <RoomRate MealPlanCode=\"RO\" RatePlanCode=\"IDH15\" RoomTypeCode=\"CDXN\">\n" +
                "                            <Rates>\n" +
                "                                <Rate AmountAfterTax=\"10064.000\" AmountBeforeTax=\"8500.000\" EffectiveDate=\"2015-10-11\"\n" +
                "                                      ExpireDate=\"2015-10-12\"/>\n" +
                "                                <Rate AmountAfterTax=\"10693.000\" AmountBeforeTax=\"9031.250\" EffectiveDate=\"2015-10-12\"\n" +
                "                                      ExpireDate=\"2015-10-16\"/>\n" +
                "                                <Rate AmountAfterTax=\"8453.760\" AmountBeforeTax=\"7140.000\" EffectiveDate=\"2015-10-16\"\n" +
                "                                      ExpireDate=\"2015-10-17\"/>\n" +
                "                            </Rates>\n" +
                "                            <Fees>\n" +
                "                                <Fee ChargeType=\"Tax\" Percent=\"18.400\" Type=\"Exclusive\"/>\n" +
                "                            </Fees>\n" +
                "                        </RoomRate>\n" +
                "                    </RoomRates>\n" +
                "                </Availability>\n" +
                "            </Availabilities>\n" +
                "        </MultiAvailabilityResponse>\n" +
                "    </SOAP-ENV:Body>\n" +
                "</SOAP-ENV:Envelope>";
    }

    @ResponseBody
    @RequestMapping(value = "/details", method = RequestMethod.POST)
    public String details(@RequestBody String xml) {
        return "<AvailabilityResponse Status=\"Successful\" Token=\"187be58c62c2f2515b5d78ee\">\n" +
                "<Availabilities>\n" +
                "<Availability CurrencyCode=\"INR\" HotelCode=\"IHG-BOMAP\">\n" +
                "<GuestCount AdultCount=\"1\" ChildCount=\"2\" ChildAges=\"8,10\"/>\n" +
                "<RoomTypes>\n" +
                "<RoomType RoomTypeCode=\"KDXN\" RoomTypeName=\"1 KING SUPERIOR NONSMOKING\">\n" +
                "<RoomTypeDescription>1 KING SUPERIOR NONSMOKING</RoomTypeDescription>\n" +
                "</RoomType>\n" +
                "<RoomType RoomTypeCode=\"CDXN\" RoomTypeName=\"DELUXE ROOM\">\n" +
                "<RoomTypeDescription>DELUXE ROOM</RoomTypeDescription>\n" +
                "</RoomType>\n" +
                "</RoomTypes>\n" +
                "<RatePlans>\n" +
                "<RatePlan RatePlanCode=\"IDH15\" RatePlanName=\"IDH15\">\n" +
                "<CancelPolicy NonRefundable=\"true\">\n" +
                "<CancelPenalties />\n" +
                "</CancelPolicy>\n" +
                "</RatePlan>\n" +
                "<RatePlan RatePlanCode=\"IDU15\" RatePlanName=\"IDU15\">\n" +
                "<CancelPolicy NonRefundable=\"false\">\n" +
                "<CancelPenalties><CancelPenalty MaxHoursBeforeCheckIn=\"48\" MinHoursBeforeCheckIn=\"0\" Nights=\"1\" NoShow=\"false\" />\n" +
                "<CancelPenalty Nights=\"1\" NoShow=\"true\" />\n" +
                "</CancelPenalties>\n" +
                "</CancelPolicy>\n" +
                "</RatePlan>\n" +
                "</RatePlans>\n" +
                "<RoomRates>\n" +
                "<RoomRate MealPlanCode=\"RO\" RatePlanCode=\"IDH15\" RoomTypeCode=\"KDXN\">\n" +
                "<Rates>\n" +
                "<Rate AmountAfterTax=\"8453.760\" AmountBeforeTax=\"7140.000\" EffectiveDate=\"2015-10-11\" ExpireDate=\"2015-10-12\" />\n" +
                "<Rate AmountAfterTax=\"8982.120\" AmountBeforeTax=\"7586.250\" EffectiveDate=\"2015-10-12\" ExpireDate=\"2015-10-16\" />\n" +
                "<Rate AmountAfterTax=\"6843.520\" AmountBeforeTax=\"5780.000\" EffectiveDate=\"2015-10-16\" ExpireDate=\"2015-10-17\" />\n" +
                "</Rates>\n" +
                "<Fees>\n" +
                "<Fee ChargeType=\"Tax\" Percent=\"18.400\" Type=\"Exclusive\" />\n" +
                "</Fees>\n" +
                "</RoomRate>\n" +
                "<RoomRate MealPlanCode=\"RO\" RatePlanCode=\"IDU15\" RoomTypeCode=\"CDXN\">\n" +
                "<Rates>\n" +
                "<Rate AmountAfterTax=\"12580.000\" AmountBeforeTax=\"10625.000\" EffectiveDate=\"2015-10-11\" ExpireDate=\"2015-10-16\" />\n" +
                "<Rate AmountAfterTax=\"10567.200\" AmountBeforeTax=\"8925.000\" EffectiveDate=\"2015-10-16\" ExpireDate=\"2015-10-17\" />\n" +
                "</Rates>\n" +
                "<Fees>\n" +
                "<Fee ChargeType=\"Tax\" Percent=\"18.400\" Type=\"Exclusive\" />\n" +
                "</Fees>\n" +
                "</RoomRate>\n" +
                "<RoomRate MealPlanCode=\"RO\" RatePlanCode=\"IDH15\" RoomTypeCode=\"CDXN\">\n" +
                "<Rates>\n" +
                "<Rate AmountAfterTax=\"10064.000\" AmountBeforeTax=\"8500.000\" EffectiveDate=\"2015-10-11\" ExpireDate=\"2015-10-12\" />\n" +
                "<Rate AmountAfterTax=\"10693.000\" AmountBeforeTax=\"9031.250\" EffectiveDate=\"2015-10-12\" ExpireDate=\"2015-10-16\" />\n" +
                "<Rate AmountAfterTax=\"8453.760\" AmountBeforeTax=\"7140.000\" EffectiveDate=\"2015-10-16\" ExpireDate=\"2015-10-17\" />\n" +
                "</Rates>\n" +
                "<Fees>\n" +
                "<Fee ChargeType=\"Tax\" Percent=\"18.400\" Type=\"Exclusive\" />\n" +
                "</Fees>\n" +
                "</RoomRate>\n" +
                "</RoomRates>\n" +
                "</Availability>\n" +
                "<Availability CurrencyCode=\"INR\" HotelCode=\"IHG-BOMAP\">\n" +
                "<GuestCount AdultCount=\"2\" ChildCount=\"1\" ChildAges=\"11\"/>\n" +
                "<RoomTypes>\n" +
                "<RoomType RoomTypeCode=\"KDXN\" RoomTypeName=\"1 KING SUPERIOR NONSMOKING\">\n" +
                "<RoomTypeDescription>1 KING SUPERIOR NONSMOKING</RoomTypeDescription>\n" +
                "</RoomType>\n" +
                "<RoomType RoomTypeCode=\"CDXN\" RoomTypeName=\"DELUXE ROOM\">\n" +
                "<RoomTypeDescription>DELUXE ROOM</RoomTypeDescription>\n" +
                "</RoomType>\n" +
                "</RoomTypes>\n" +
                "<RatePlans>\n" +
                "<RatePlan RatePlanCode=\"IDH15\" RatePlanName=\"IDH15\">\n" +
                "<CancelPolicy NonRefundable=\"true\">\n" +
                "<CancelPenalties />\n" +
                "</CancelPolicy>\n" +
                "</RatePlan>\n" +
                "<RatePlan RatePlanCode=\"IDU15\" RatePlanName=\"IDU15\">\n" +
                "<CancelPolicy NonRefundable=\"false\">\n" +
                "<CancelPenalties>\n" +
                "<CancelPenalty MaxHoursBeforeCheckIn=\"48\" MinHoursBeforeCheckIn=\"0\" Nights=\"1\" NoShow=\"false\" />\n" +
                "<CancelPenalty Nights=\"1\" NoShow=\"true\" />\n" +
                "</CancelPenalties>\n" +
                "</CancelPolicy>\n" +
                "</RatePlan>\n" +
                "</RatePlans>\n" +
                "<RoomRates>\n" +
                "<RoomRate MealPlanCode=\"RO\" RatePlanCode=\"IDH15\" RoomTypeCode=\"KDXN\"><Rates>\n" +
                "<Rate AmountAfterTax=\"8453.760\" AmountBeforeTax=\"7140.000\" EffectiveDate=\"2015-10-11\" ExpireDate=\"2015-10-12\" />\n" +
                "<Rate AmountAfterTax=\"8982.120\" AmountBeforeTax=\"7586.250\" EffectiveDate=\"2015-10-12\" ExpireDate=\"2015-10-16\" />\n" +
                "<Rate AmountAfterTax=\"6843.520\" AmountBeforeTax=\"5780.000\" EffectiveDate=\"2015-10-16\" ExpireDate=\"2015-10-17\" />\n" +
                "</Rates>\n" +
                "<Fees>\n" +
                "<Fee ChargeType=\"Tax\" Percent=\"18.400\" Type=\"Exclusive\" />\n" +
                "</Fees>\n" +
                "</RoomRate>\n" +
                "<RoomRate MealPlanCode=\"RO\" RatePlanCode=\"IDU15\" RoomTypeCode=\"CDXN\">\n" +
                "<Rates>\n" +
                "<Rate AmountAfterTax=\"12580.000\" AmountBeforeTax=\"10625.000\" EffectiveDate=\"2015-10-11\" ExpireDate=\"2015-10-16\" />\n" +
                "<Rate AmountAfterTax=\"10567.200\" AmountBeforeTax=\"8925.000\" EffectiveDate=\"2015-10-16\" ExpireDate=\"2015-10-17\" />\n" +
                "</Rates>\n" +
                "<Fees>\n" +
                "<Fee ChargeType=\"Tax\" Percent=\"18.400\" Type=\"Exclusive\" />\n" +
                "</Fees>\n" +
                "</RoomRate>\n" +
                "<RoomRate MealPlanCode=\"RO\" RatePlanCode=\"IDH15\" RoomTypeCode=\"CDXN\">\n" +
                "<Rates>\n" +
                "<Rate AmountAfterTax=\"10064.000\" AmountBeforeTax=\"8500.000\" EffectiveDate=\"2015-10-11\" ExpireDate=\"2015-10-12\" />\n" +
                "<Rate AmountAfterTax=\"10693.000\" AmountBeforeTax=\"9031.250\" EffectiveDate=\"2015-10-12\" ExpireDate=\"2015-10-16\" />\n" +
                "<Rate AmountAfterTax=\"8453.760\" AmountBeforeTax=\"7140.000\" EffectiveDate=\"2015-10-16\" ExpireDate=\"2015-10-17\" />\n" +
                "</Rates>\n" +
                "<Fees>\n" +
                "<Fee ChargeType=\"Tax\" Percent=\"18.400\" Type=\"Exclusive\" />\n" +
                "</Fees>\n" +
                "</RoomRate>\n" +
                "</RoomRates>\n" +
                "</Availability>\n" +
                "</Availabilities>\n" +
                "</AvailabilityResponse>";
    }
}
