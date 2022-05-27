package com.klasha.android.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Card (
    val number: String,
    val expiryMonth: Int,
    val expiryYear: Int,
    val cvv: Int,
    val name: String = ""
)

data class MobileMoney(
    val voucher: String,
    val network: Network
)

data class Charge (
    val amount: Double,
    val email: String,
    val fullName: String,
    val card: Card?,
    val mobileMoney: MobileMoney? = null,
    val phone: String = "",
    val accountBank: String = "",
    val transactionReference: String = UUID.randomUUID().toString())

data class BankTransferResp(
    @SerializedName("transfer_reference")
    val transferReference: String,
    @SerializedName("transfer_account")
    val transferAccount: String,
    @SerializedName("transfer_bank")
    val transferBank: String,
    @SerializedName("account_expiration")
    val accountExpiration: String,
    @SerializedName("transfer_note")
    val transferMode: String,
    @SerializedName("transfer_amount")
    val transferAmount: Double,
    val mode: String
)

data class BankCodeResponse(
    val id: Double,
    val code: String,
    val name: String,
    val imageUrl: String
)

data class USSDResponse(
    val status: String,
    val message: String,
    val data: Data,
    val meta: Meta
){
    data class Meta(val authorization: Authorization)

    data class Data(
        val id: Double,
        val amount: Double,
        @SerializedName("charged_amount")
        val chargedAmount: Double,
        @SerializedName("app_fee")
        val appFee: Double,
        val status: String,
        @SerializedName("fraud_status")
        val fraudStatus: String)

    data class Authorization(val mode: String, val note: String)
}

enum class Currency {
    AFN, ALL, DZD, AOA, ARS, AMD, AWG, AZM, BSD, BHD, BDT, BBD, BYR, BZD, BMD, BAM, BWP, BRL, BGN,
    BIF, KHR, CAD, CVE, KYD, CLP, CNY, COP, KMF, CDF, CRC, HRK, CUP, CYP, CZK, DJF, DOP, EGP, ERN,
    EEK, ETB, FJD, XAF, GMD, GEL, GHS, GIP, DKK, GTQ, GNF, GYD, HTG, HNL, HKD, HUF, ISK, INR, IDR,
    IRR, IQD, ILS, JMD, JPY, JOD, KZT, KES, KWD, KGS, LVL, LBP, LRD, LTL, MOP, MWK, MYR, MVR, MTL,
    MRO, MUR, MXN, MXV, MNT, MZM, MMK, NPR, NIO, NGN, OMR, PKR, PAB, PGK, PYG, PEN, PHP, PLN, QAR,
    ROL, RWF, SHP, XCD, WST, STD, SAR, SCR, SLL, SGD, SKK, SIT, SBD, SOS, ZAR, EUR, LKR, SDD, SRD,
    NOK, SZL, SEK, CHF, SYP, TWD, TJS, TZS, THB, XOF, NZD, TOP, TTD, TND, TRL, TMM, AUD, UGX, UAH,
    AED, GBP, USD, UYU, UZS, VUV, VEB, VND, XPF, MAD, YER, ZMK, ZWD,
}

enum class Network{
    MTN
}

internal enum class PaymentType{
    card
}

internal enum class MPESAOption{
    mpesa
}

enum class CountryCode {
    AF, AL, DZ, AS, AD, AO, AI, AG, AR, AM, AW, AU, AT, AZ, BS, BH, BD, BB, BY, BE, BZ, BJ, BM,
    BA, BW, BV, BR, BG, BF, BI, KH, CM, CA, CV, KY, CF, TD, CL, CN, CX, CO, KM, CD, CK, CR, CI,
    HR, CU, CY, CZ, DK, DJ, DM, DO, EC, EG, SV, GQ, ER, EE, ET, FO, FJ, FI, FR, GF, PF, TF, GA,
    GM, GE, DE, GH, GI, GR, GL, GD, GP, GU, GT, GN, GW, GY, HT, HN, HK, HU, IS, IN, ID, IR, IQ,
    IE, IL, IT, JM, JP, JO, KZ, KE, KI, KW, KG, LV, LB, LR, LI, LT, LU, MO, MW, MY, MV, ML, MT,
    MH, MQ, MR, MU, YT, MX, FM, MC, MN, MS, MA, MZ, MM, NA, NR, NP, NL, NC, NZ, NI, NE, NG, NU,
    NF, MP, NO, OM, PK, PW, PA, PG, PY, PE, PH, PN, PL, PT, PR, QA, RE, RO, RW, SH, KN, LC, PM,
    VC, WS, SM, ST, SA, SN, SC, SL, SG, SK, SI, SB, SO, ZA, ES, LK, SD, SR, SJ, SZ, SE, CH, SY,
    TW, TJ, TZ, TH, TL, TG, TK, TO, TT, TN, TR, TM, TC, TV, UG, UA, AE, GB, US, UY, UZ, VU, VE,
    VN, WF, EH, YE, ZM, ZW,
}

enum class Country(val countryCode: CountryCode, val currency: Currency, val symbol: String) {
    AFGHANISTAN (CountryCode.AF, Currency.AFN, "؋"),
    ALBANIA (CountryCode.AL, Currency.ALL, "Lek"),
    ALGERIA (CountryCode.DZ, Currency.DZD, "دج"),
    AMERICAN_SAMOA (CountryCode.AS, Currency.USD, "$"),
    ANDORRA (CountryCode.AD, Currency.EUR, "€"),
    ANGOLA (CountryCode.AO, Currency.AOA, "Kz"),
    ANGUILLA (CountryCode.AI, Currency.XCD, "$"),
    ANTIGUA_AND_BARBUDA (CountryCode.AG, Currency.XCD , "$"),
    ARGENTINA (CountryCode.AR, Currency.ARS, "$"),
    ARMENIA (CountryCode.AM, Currency.AMD, "֏"),
    ARUBA (CountryCode.AW, Currency.AWG, "ƒ"),
    AUSTRALIA (CountryCode.AU, Currency.AUD, "$"),
    AUSTRIA (CountryCode.AT, Currency.EUR, "€"),
    AZERBAIJAN (CountryCode.AZ, Currency.AZM, "AZM"),
    BAHAMAS (CountryCode.BS, Currency.BSD, "$"),
    BAHRAIN (CountryCode.BH, Currency.BHD, "BD"),
    BANGLADESH (CountryCode.BD, Currency.BDT, "৳"),
    BARBADOS (CountryCode.BB, Currency.BBD, "$"),
    BELARUS (CountryCode.BY, Currency.BYR, "Br"),
    BELGIUM (CountryCode.BE, Currency.EUR, "€"),
    BELIZE (CountryCode.BZ, Currency.BZD, "$"),
    BENIN (CountryCode.BJ, Currency.XOF, "CFA"),
    BERMUDA (CountryCode.BM, Currency.BMD, "$"),
    BOSNIA_AND_HERZEGOVINA (CountryCode.BA, Currency.BAM, "KM"),
    BOTSWANA (CountryCode.BW, Currency.BWP, "P"),
    BOUVET_ISLAND (CountryCode.BV, Currency.NOK, "kr"),
    BRAZIL (CountryCode.BR, Currency.BRL, "R$"),
    BULGARIA (CountryCode.BG, Currency.BGN, "лв"),
    BURKINA_FASO (CountryCode.BF, Currency.XOF, "CFA"),
    BURUNDI (CountryCode.BI, Currency.BIF, "CFA"),
    CAMBODIA (CountryCode.KH, Currency.KHR, "៛"),
    CAMEROON (CountryCode.CM, Currency.XAF, "FCFA"),
    CANADA (CountryCode.CA, Currency.CAD, "$"),
    CAPE_VERDE (CountryCode.CV, Currency.CVE, "$"),
    CAYMAN_ISLANDS (CountryCode.KY, Currency.KYD, "$"),
    CENTRAL_AFRICAN_REPUBLIC (CountryCode.CF, Currency.XAF, "FCFA"),
    CHAD (CountryCode.TD, Currency.XAF, "FCFA"),
    CHILE (CountryCode.CL, Currency.CLP, "$"),
    CHINA (CountryCode.CN, Currency.CNY, "¥"),
    CHRISTMAS_ISLAND (CountryCode.CX, Currency.AUD, "$"),
    COLOMBIA (CountryCode.CO, Currency.COP, "$"),
    COMOROS (CountryCode.KM, Currency.KMF, "CF"),
    CONGO (CountryCode.CD, Currency.CDF, "FC"),
    COOK_ISLANDS (CountryCode.CK, Currency.NZD, "$"),
    COSTA_RICA (CountryCode.CR, Currency.CRC, "₡"),
    COTE_DIVOIRE (CountryCode.CI, Currency.XOF, "CFA"),
    CROATIA (CountryCode.HR, Currency.HRK, "kn"),
    CUBA (CountryCode.CU, Currency.CUP, "₱"),
    CYPRUS (CountryCode.CY, Currency.CYP, "€"),
    CZECH_REPUBLIC (CountryCode.CZ, Currency.CZK, "Kč"),
    DENMARK (CountryCode.DK, Currency.DKK, "kr"),
    DJIBOUTI (CountryCode.DJ, Currency.DJF, "Fdj"),
    DOMINICA (CountryCode.DM, Currency.XCD, "$"),
    DOMINICAN_REPUBLIC (CountryCode.DO, Currency.DOP, "RD$"),
    ECUADOR (CountryCode.EC, Currency.USD, "$"),
    EGYPT (CountryCode.EG, Currency.EGP, "E£"),
    EL_SALVADOR (CountryCode.SV, Currency.USD, "$"),
    EQUATORIAL_GUINEA (CountryCode.GQ, Currency.XAF, "FCFA"),
    ERITREA (CountryCode.ER, Currency.ERN, "ናቕፋ"),
    ESTONIA (CountryCode.EE, Currency.EEK, "€"),
    ETHIOPIA (CountryCode.ET, Currency.ETB, "Br"),
    FAROE_ISLANDS (CountryCode.FO, Currency.DKK, "kr"),
    FIJI (CountryCode.FJ, Currency.FJD, "$"),
    FINLAND (CountryCode.FI, Currency.EUR, "€"),
    FRANCE (CountryCode.FR, Currency.EUR, "€"),
    FRENCH_GUIANA (CountryCode.GF, Currency.EUR, "€"),
    FRENCH_POLYNESIA (CountryCode.PF, Currency.XPF, "₣"),
    FRENCH_SOUTHERN_TERRITORIES (CountryCode.TF, Currency.EUR, "€"),
    GABON (CountryCode.GA, Currency.XAF, "FCFA"),
    GAMBIA (CountryCode.GM, Currency.GMD, "D"),
    GEORGIA (CountryCode.GE, Currency.GEL, "GEL"),
    GERMANY (CountryCode.DE, Currency.EUR, "€"),
    GHANA (CountryCode.GH, Currency.GHS, "GH₵"),
    GIBRALTAR (CountryCode.GI, Currency.GIP, "£"),
    GREECE (CountryCode.GR, Currency.EUR, "€"),
    GREENLAND (CountryCode.GL, Currency.DKK, "Kr."),
    GRENADA (CountryCode.GD, Currency.XCD, "$"),
    GUADELOUPE (CountryCode.GP, Currency.EUR, "€"),
    GUAM (CountryCode.GU, Currency.USD, "$"),
    GUATEMALA (CountryCode.GT, Currency.GTQ, "Q"),
    GUINEA (CountryCode.GN, Currency.GNF, "GFr"),
    GUINEA_BISSAU (CountryCode.GW, Currency.XOF, "CFA"),
    GUYANA (CountryCode.GY, Currency.GYD, "GY$"),
    HAITI (CountryCode.HT, Currency.HTG, "G"),
    HONDURAS (CountryCode.HN, Currency.HNL, "L"),
    HONG_KONG (CountryCode.HK, Currency.HKD, "$"),
    HUNGARY (CountryCode.HU, Currency.HUF, "Ft"),
    ICELAND (CountryCode.IS, Currency.ISK, "kr"),
    INDIA (CountryCode.IN, Currency.INR, "₹"),
    INDONESIA (CountryCode.ID, Currency.IDR, "Rp"),
    IRAN (CountryCode.IR, Currency.IRR, "﷼"),
    IRAQ (CountryCode.IQ, Currency.IQD, "IQD"),
    IRELAND (CountryCode.IE, Currency.EUR, "€"),
    ISRAEL (CountryCode.IL, Currency.ILS, "₪"),
    ITALY (CountryCode.IT, Currency.EUR, "€"),
    JAMAICA (CountryCode.JM, Currency.JMD, "$"),
    JAPAN (CountryCode.JP, Currency.JPY, "¥"),
    JORDAN (CountryCode.JO, Currency.JOD, "د.ا"),
    KAZAKHSTAN (CountryCode.KZ, Currency.KZT, "лв"),
    KENYA (CountryCode.KE, Currency.KES, "Ksh"),
    KIRIBATI (CountryCode.KI, Currency.AUD, "$"),
    KUWAIT (CountryCode.KW, Currency.KWD, "KD"),
    KYRGYZSTAN (CountryCode.KG, Currency.KGS, "Лв"),
    LATVIA (CountryCode.LV, Currency.LVL, "€"),
    LEBANON (CountryCode.LB, Currency.LBP, "LBP"),
    LIBERIA (CountryCode.LR, Currency.LRD, "$"),
    LIECHTENSTEIN (CountryCode.LI, Currency.CHF, "CHf"),
    LITHUANIA (CountryCode.LT, Currency.LTL, "€"),
    LUXEMBOURG (CountryCode.LU, Currency.EUR, "€"),
    MACAO (CountryCode.MO, Currency.MOP, "MOP$"),
    MALAWI (CountryCode.MW, Currency.MWK, "MK"),
    MALAYSIA (CountryCode.MY, Currency.MYR, "RM"),
    MALDIVES (CountryCode.MV, Currency.MVR, "Rf"),
    MALI (CountryCode.ML, Currency.XOF, "CFA"),
    MALTA (CountryCode.MT, Currency.MTL, "€"),
    MARSHALL_ISLANDS (CountryCode.MH, Currency.USD, "$"),
    MARTINIQUE (CountryCode.MQ, Currency.EUR, "€"),
    MAURITANIA (CountryCode.MR, Currency.MRO, "UM"),
    MAURITIUS (CountryCode.MU, Currency.MUR, "₨"),
    MAYOTTE (CountryCode.YT, Currency.EUR, "€"),
    MEXICO (CountryCode.MX, Currency.MXN, "$"),
    MICRONESIA_FEDERATED_STATES_OF	(CountryCode.FM, Currency.USD, "$"),
    MONACO (CountryCode.MC, Currency.EUR, "€"),
    MONGOLIA (CountryCode.MN, Currency.MNT, "₮"),
    MONTSERRAT (CountryCode.MS, Currency.XCD, "$"),
    MOROCCO (CountryCode.MA, Currency.MAD, "DH"),
    MOZAMBIQUE (CountryCode.MZ, Currency.MZM, "MT"),
    MYANMAR (CountryCode.MM, Currency.MMK, "K"),
    NAMIBIA (CountryCode.NA, Currency.ZAR, "R"),
    NAURU (CountryCode.NR, Currency.AUD, "$"),
    NEPAL (CountryCode.NP, Currency.NPR, "₨"),
    NETHERLANDS (CountryCode.NL, Currency.EUR, "€"),
    NEW_CALEDONIA (CountryCode.NC, Currency.XPF, "₣"),
    NEW_ZEALAND (CountryCode.NZ, Currency.NZD, "$"),
    NICARAGUA (CountryCode.NI, Currency.NIO, "C$"),
    NIGER (CountryCode.NE, Currency.XOF, "CFA"),
    NIGERIA (CountryCode.NG, Currency.NGN, "₦"),
    NIUE (CountryCode.NU, Currency.NZD, "$"),
    NORFOLK_ISLAND (CountryCode.NF, Currency.AUD, "$"),
    NORTHERN_MARIANA_ISLANDS (CountryCode.MP, Currency.USD, "$"),
    NORWAY (CountryCode.NO, Currency.NOK, "kr"),
    OMAN (CountryCode.OM, Currency.OMR, "﷼"),
    PAKISTAN (CountryCode.PK, Currency.PKR, "₨"),
    PALAU (CountryCode.PW, Currency.USD, "$"),
    PANAMA (CountryCode.PA, Currency.PAB, "B/."),
    PAPUA_NEW_GUINEA (CountryCode.PG, Currency.PGK, "K"),
    PARAGUAY (CountryCode.PY, Currency.PYG, "₲"),
    PERU (CountryCode.PE, Currency.PEN, "S/"),
    PHILIPPINES (CountryCode.PH, Currency.PHP, "₱"),
    PITCAIRN (CountryCode.PN, Currency.NZD, "$"),
    POLAND (CountryCode.PL, Currency.PLN, "zł"),
    PORTUGAL (CountryCode.PT, Currency.EUR, "€"),
    PUERTO_RICO (CountryCode.PR, Currency.USD, "$"),
    QATAR (CountryCode.QA, Currency.QAR, "QR"),
    REUNION (CountryCode.RE, Currency.EUR, "€"),
    ROMANIA (CountryCode.RO, Currency.ROL, "lei"),
    RWANDA (CountryCode.RW, Currency.RWF, "R₣"),
    SAINT_HELENA (CountryCode.SH, Currency.SHP, "£"),
    SAINT_KITTS_AND_NEVIS (CountryCode.KN, Currency.XCD, "$"),
    SAINT_LUCIA (CountryCode.LC, Currency.XCD, "$"),
    SAINT_PIERRE_AND_MIQUELON (CountryCode.PM, Currency.EUR, "€"),
    SAINT_VINCENT_AND_THE_GRENADINES (CountryCode.VC, Currency.XCD, "$"),
    SAMOA (CountryCode.WS, Currency.WST, "SAT"),
    SAN_MARINO (CountryCode.SM, Currency.EUR, "€"),
    SAO_TOME_AND_PRINCIPE (CountryCode.ST, Currency.STD, "Db"),
    SAUDI_ARABIA (CountryCode.SA, Currency.SAR, "SR"),
    SENEGAL (CountryCode.SN, Currency.XOF, "CFA"),
    SEYCHELLES (CountryCode.SC, Currency.SCR, "SRe"),
    SIERRA_LEONE (CountryCode.SL, Currency.SLL, "Le"),
    SINGAPORE (CountryCode.SG, Currency.SGD, "S$"),
    SLOVAKIA (CountryCode.SK, Currency.SKK, "Sk"),
    SLOVENIA (CountryCode.SI, Currency.SIT, "€"),
    SOLOMON_ISLANDS (CountryCode.SB, Currency.SBD, "Si$"),
    SOMALIA (CountryCode.SO, Currency.SOS, "Sh.so."),
    SOUTH_AFRICA (CountryCode.ZA, Currency.ZAR, "R"),
    SPAIN (CountryCode.ES, Currency.EUR, "€"),
    SRI_LANKA (CountryCode.LK, Currency.LKR, "Rs"),
    SUDAN (CountryCode.SD, Currency.SDD, "£Sd"),
    SURINAME (CountryCode.SR, Currency.SRD, "$"),
    SVALBARD_AND_JAN_MAYEN (CountryCode.SJ, Currency.NOK, "kr"),
    SWAZILAND (CountryCode.SZ, Currency.SZL, "L"),
    SWEDEN (CountryCode.SE, Currency.SEK, "kr"),
    SWITZERLAND (CountryCode.CH, Currency.CHF, "CHf"),
    SYRIAN_ARAB_REPUBLIC (CountryCode.SY, Currency.SYP, "£S"),
    TAIWAN (CountryCode.TW, Currency.TWD, "NT$"),
    TAJIKISTAN (CountryCode.TJ, Currency.TJS, "TJS"),
    TANZANIA (CountryCode.TZ, Currency.TZS, "TZS"),
    THAILAND (CountryCode.TH, Currency.THB, "฿"),
    TIMOR_LESTE	(CountryCode.TL, Currency.USD, "$"),
    TOGO (CountryCode.TG, Currency.XOF, "CFA"),
    TOKELAU (CountryCode.TK, Currency.NZD, "$"),
    TONGA (CountryCode.TO, Currency.TOP, "T$"),
    TRINIDAD_AND_TOBAGO (CountryCode.TT, Currency.TTD,"TT$"),
    TUNISIA (CountryCode.TN, Currency.TND, "DT"),
    TURKEY (CountryCode.TR, Currency.TRL, "₺"),
    TURKMENISTAN (CountryCode.TM, Currency.TMM, "T"),
    TURKS_AND_CAICOS_ISLANDS (CountryCode.TC, Currency.USD, "$"),
    TUVALU (CountryCode.TV, Currency.AUD, "$"),
    UGANDA (CountryCode.UG, Currency.UGX, "USh"),
    UKRAINE (CountryCode.UA, Currency.UAH, "₴"),
    UNITED_ARAB_EMIRATES (CountryCode.AE, Currency.AED, "د.إ"),
    UNITED_KINGDOM (CountryCode.GB, Currency.GBP, "£"),
    UNITED_STATES (CountryCode.US, Currency.USD, "$"),
    URUGUAY (CountryCode.UY, Currency.UYU, "$ U"),
    UZBEKISTAN (CountryCode.UZ, Currency.UZS, "лв"),
    VANUATU (CountryCode.VU, Currency.VUV, "VT"),
    VENEZUELA (CountryCode.VE, Currency.VEB, "Bs."),
    VIET_NAM (CountryCode.VN, Currency.VND, "₫"),
    WALLIS_AND_FUTUNA (CountryCode.WF, Currency.XPF, "₣"),
    WESTERN_SAHARA (CountryCode.EH, Currency.MAD, "DH"),
    YEMEN (CountryCode.YE, Currency.YER, "﷼"),
    ZAMBIA (CountryCode.ZM, Currency.ZMK, "ZK"),
    ZIMBABWE (CountryCode.ZW, Currency.ZWD, "Z$")
}