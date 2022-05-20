package woman.calendar.every.day.health.data.providers

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.domain.ArticlesProvider
import woman.calendar.every.day.health.domain.model.Article
import woman.calendar.every.day.health.domain.model.ArticleTitleColor.WHITE
import woman.calendar.every.day.health.domain.model.ArticleType.*
import woman.calendar.every.day.health.utils.IdHelper

class ArticlesProviderImpl(val context: Context) : ArticlesProvider {
    private val articles = mutableMapOf<Int, Article>()
    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun getArticleGroups(): List<Article> {
//        var idHelper = IdHelper()
        val articles = mutableListOf<Article>()
        val internalArticles = mutableListOf<Article>()
        for (i in 0..5) {
            internalArticles.add(
                Article(
                    id = IdHelper.getId(),
                    title = "Internal article $i",
                    content = context.resources.getString(R.string._6_Things_About_Emergency_Contraception_Your_Doctor_Wants_You_to_Know_content),
                    internalArticlesId = emptyList(),
                    type = YOUR_CYCLE_PHASE,
//                    bigImage = Uri.parse("https://cdn1.flamp.ru/06fce15cc8b283dfe7df8b02d7115a72.png"),
//                    smallImage = Uri.parse("https://cdn1.flamp.ru/06fce15cc8b283dfe7df8b02d7115a72.png")
                    bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.drawable.im_big_placeholder_jpg),
                    smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.drawable.im_placeholder),
                    parentType = YOUR_CYCLE_PHASE
                )
            )
        }
        for (i in 0..10) {
            articles.add(
                Article(
                    id = IdHelper.getId(),
                    title = "Group $i",
                    content = context.resources.getString(R.string.Early_Signs_of_Pregnancy_content),
                    internalArticlesId = internalArticles.map { it.id },
                    type = YOUR_CYCLE_PHASE,
                    /*   bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.drawable.im_big_placeholder_jpg),
                       smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.drawable.im_placeholder)
                   )*/
                    bigImage = Uri.parse("https://cdn1.flamp.ru/06fce15cc8b283dfe7df8b02d7115a72.png"),
                    smallImage = Uri.parse("https://cdn1.flamp.ru/06fce15cc8b283dfe7df8b02d7115a72.png"),
                    parentType = YOUR_CYCLE_PHASE
                )
            )
        }
        this.articles.putAll(articles.associateBy { it.id })
        this.articles.putAll(internalArticles.associateBy { it.id })
        return articles.toList()
    }

    override fun getArticle(id: Int): Article? {
        return articles[id]
    }

    override fun getArticles(): List<Article> {
        articles.putAll(getReproductiveHealthArticles().associateBy { it.id })
        articles.putAll(getSexArticles().associateBy { it.id })
        articles.putAll(getYourCyclePhase().associateBy { it.id })
        articles.putAll(getTheLatest().associateBy { it.id })
        articles.putAll(getLGBTQ().associateBy { it.id })
        articles.putAll(getTipsForPainFreeCycle().associateBy { it.id })
        articles.putAll(getNutritionAndFitness().associateBy { it.id })
        return articles.values.toList()
    }

    private fun getReproductiveHealthArticles(): List<Article> {
        val articles = listOf(
            /** early signs of pregnancy*/
            Article(
                id = 100500,
                title = context.resources.getString(R.string.Early_Signs_of_Pregnancy),
                content = context.resources.getString(R.string.Early_Signs_of_Pregnancy_content),
                type = EARLY_SIGNS_OF_PREGNANCY,
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_early_signs_of_pregnancy),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_early_signs_of_pregnancy),
                //                bigImage = Uri.parse("https://cdn1.flamp.ru/06fce15cc8b283dfe7df8b02d7115a72.png"),
                //                smallImage = Uri.parse("https://cdn1.flamp.ru/06fce15cc8b283dfe7df8b02d7115a72.png"),
                parentType = REPRODUCTIVE_HEALTH
            ),
            Article(
                id = 100501,
                title = context.resources.getString(R.string.Hate_the_Wait_What_Are_the_Earliest_Signs_of_Pregnancy),
                content = context.resources.getString(R.string.Hate_the_Wait_What_Are_the_Earliest_Signs_of_Pregnancy_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_hate_the_wait),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_hate_the_wait_small),
                parentType = EARLY_SIGNS_OF_PREGNANCY
            ),

            Article(
                id = 100502,
                title = context.resources.getString(R.string._8_Early_Pregnancy_Symptoms_That_Feel_A_Lot_Like_PMS),
                content = context.resources.getString(R.string._8_Early_Pregnancy_Symptoms_That_Feel_A_Lot_Like_PMS_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_8_early_pregnancy_symtoms),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_8_early_pregnancy_symtoms_small),
                parentType = EARLY_SIGNS_OF_PREGNANCY
            ),
            Article(
                id = 100503,
                title = context.resources.getString(R.string.How_Soon_Can_I_Take_a_Pregnancy_Test),
                content = context.resources.getString(R.string.How_Soon_Can_I_Take_a_Pregnancy_Test_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_soon_can_i_take_test),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_soon_can_i_take_test_small),
                parentType = EARLY_SIGNS_OF_PREGNANCY
            ),
            Article(
                id = 100504,
                title = context.resources.getString(R.string.Pregnancy_Paranoia_How_to_Handle_It),
                content = context.resources.getString(R.string.Pregnancy_Paranoia_How_to_Handle_It_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_pregn_paranoia),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_pregn_paranoia_small),
                parentType = EARLY_SIGNS_OF_PREGNANCY
            ),
            Article(
                id = 100505,
                title = context.resources.getString(R.string.Could_You_Have_Period_and_Be_Pregnant),
                content = context.resources.getString(R.string.Could_You_Have_Period_and_Be_Pregnant_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_could_you_have_period),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_could_you_have_period_small),
                parentType = EARLY_SIGNS_OF_PREGNANCY
            ),
            Article(
                id = 100506,
                title = context.resources.getString(R.string.Negative_Result_Am_I_Definitely_Not_Pregnant),
                content = context.resources.getString(R.string.Negative_Result_Am_I_Definitely_Not_Pregnant_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_negative_result),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_negative_result_small),
                parentType = EARLY_SIGNS_OF_PREGNANCY
            ),
            Article(
                id = 100507,
                title = context.resources.getString(R.string._6_Things_About_Emergency_Contraception_Your_Doctor_Wants_You_to_Know),
                content = context.resources.getString(R.string._6_Things_About_Emergency_Contraception_Your_Doctor_Wants_You_to_Know_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_6_things_about_emergency),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_6_things_about_emergency_small),
                parentType = EARLY_SIGNS_OF_PREGNANCY
            ),
            Article(
                id = 100508,
                title = context.resources.getString(R.string.The_Pill_vs_Pregnancy_ALL_Your_Questions_Answered),
                content = context.resources.getString(R.string.The_Pill_vs_Pregnancy_ALL_Your_Questions_Answered_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_the_pill_vs_pregnancy),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_the_pill_vs_pregnancy_small),
                parentType = EARLY_SIGNS_OF_PREGNANCY
            ),
            Article(
                id = 100509,
                title = context.resources.getString(R.string.Positive_Pregnancy_Test_What_s_Next),
                content = context.resources.getString(R.string.Positive_Pregnancy_Test_What_s_Next_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_positive_pregnancy_test),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_positive_pregnancy_test_small),
                parentType = EARLY_SIGNS_OF_PREGNANCY
            ),
            /**vaginal discharge color*/
            Article(
                id = 100510,
                title = context.resources.getString(R.string.Vaginal_Discharge_Color_Guide),
                content = context.resources.getString(R.string.Vaginal_Discharge_Color_Guide_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_vaginal_discharge_color_guide),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_vaginal_discharge_color_guide_small),
                type = VAGINAL_DISCHARGE_COLOR,
                parentType = REPRODUCTIVE_HEALTH
            ),
            Article(
                id = 100511,
                title = context.resources.getString(R.string.White_Discharge_Why_Do_You_Have_Milky_Vaginal_Fluid),
                content = context.resources.getString(R.string.White_Discharge_Why_Do_You_Have_Milky_Vaginal_Fluid_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_white_discharge),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_white_discharge),
                parentType = VAGINAL_DISCHARGE_COLOR
            ),
            Article(
                id = 100512,
                title = context.resources.getString(R.string.Yellow_Discharge_When_and_Why_It_Happens),
                content = context.resources.getString(R.string.Yellow_Discharge_When_and_Why_It_Happens_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_yellow_discharge),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_yellow_discharge),
                parentType = VAGINAL_DISCHARGE_COLOR
            ),
            Article(
                id = 100513,
                title = context.resources.getString(R.string.White_Clumpy_Discharge_Why_Does_Discharge_Look_Like_Cottage_Cheese),
                content = context.resources.getString(R.string.White_Clumpy_Discharge_Why_Does_Discharge_Look_Like_Cottage_Cheese_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_white_clumpy_discharge),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_white_clumpy_discharge),
                parentType = VAGINAL_DISCHARGE_COLOR
            ),
            Article(
                id = 100514,
                title = context.resources.getString(R.string.Transparent_Discharge_What_Is_This_Clear_Fluid_),
                content = context.resources.getString(R.string.Transparent_Discharge_What_Is_This_Clear_Fluid_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_transparent_discharge),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_transparent_discharge),
                parentType = VAGINAL_DISCHARGE_COLOR
            ),
            Article(
                id = 100515,
                title = context.resources.getString(R.string.Brown_Discharge_What_It_Means_Depending_On_When_You_Have_I_),
                content = context.resources.getString(R.string.Brown_Discharge_What_It_Means_Depending_On_When_You_Have_I_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_brown_discharge),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_brown_discharge),
                parentType = VAGINAL_DISCHARGE_COLOR
            ),
            Article(
                id = 100516,
                title = context.resources.getString(R.string.Pink_Discharge_What_It_Means_and_What_To_Do),
                content = context.resources.getString(R.string.Pink_Discharge_What_It_Means_and_What_To_Do_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_pink_discharge),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_pink_discharge_small),
                parentType = VAGINAL_DISCHARGE_COLOR
            ),
            Article(
                id = 100517,
                title = context.resources.getString(R.string.Green_Discharge_Is_It_Cause_for_Concern_),
                content = context.resources.getString(R.string.Green_Discharge_Is_It_Cause_for_Concern_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_green_discharge),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_green_discharge),
                parentType = VAGINAL_DISCHARGE_COLOR
            ),
            Article(
                id = 100518,
                title = context.resources.getString(R.string.Grey_Discharge_Possible_Reasons_Explaine_),
                content = context.resources.getString(R.string.Grey_Discharge_Possible_Reasons_Explaine_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_grey_discharge),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_grey_discharge),
                parentType = VAGINAL_DISCHARGE_COLOR
            ),
            /**How COVID Changes Your Cycle*/
            Article(
                id = 100519,
                title = context.resources.getString(R.string.How_COVID_Changes_Your_Cycle),
                content = context.resources.getString(R.string.How_COVID_Changes_Your_Cycle_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_covid_changes_cycle),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_covid_changes_cycle),
                type = COVID,
                parentType = REPRODUCTIVE_HEALTH
            ),
            Article(
                id = 100520,
                title = context.resources.getString(R.string.Can_the_COVID_Vaccine_Delay_Periods_),
                content = context.resources.getString(R.string.Can_the_COVID_Vaccine_Delay_Periods_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_can_the_vaccine_delay_period),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_can_the_vaccine_delay_period),
                parentType = COVID
            ),
            Article(
                id = 100521,
                title = context.resources.getString(R.string.Does_the_COVID_Vaccine_Cause_Heavier_and_More_Painful_Periods_),
                content = context.resources.getString(R.string.Does_the_COVID_Vaccine_Cause_Heavier_and_More_Painful_Periods_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_does_covid_vaccine_cause_heavier),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_does_covid_vaccine_cause_heavier),
                parentType = COVID
            ),
            Article(
                id = 100522,
                title = context.resources.getString(R.string.Is_the_COVID_Vaccine_Making_PMS_Worse_),
                content = context.resources.getString(R.string.Is_the_COVID_Vaccine_Making_PMS_Worse_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_is_the_covid_vaccine_making_pms),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_is_the_covid_vaccine_making_pms),
                parentType = COVID
            ),
            Article(
                id = 100523,
                title = context.resources.getString(R.string._4_Myths_About_the_COVID_Vaccine_and_Fertility_Debunked),
                content = context.resources.getString(R.string._4_Myths_About_the_COVID_Vaccine_and_Fertility_Debunked_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_4_myths_about_covid),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_4_myths_about_covid),
                parentType = COVID
            ),
            Article(
                id = 100524,
                title = context.resources.getString(R.string.How_Having_COVID_Impacts_Your_Cycle),
                content = context.resources.getString(R.string.How_Having_COVID_Impacts_Your_Cycle_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_having_covid_impacts_your_cycle),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_having_covid_impacts_your_cycle),
                parentType = COVID
            ),
            /**without group*/
            Article(
                id = 100525,
                title = context.resources.getString(R.string.How_Soon_Can_I_Take_a_Pregnancy_Test),
                content = context.resources.getString(R.string.How_Soon_Can_I_Take_a_Pregnancy_Test_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_soon_can_i_take_test),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_soon_can_i_take_test),
                parentType = REPRODUCTIVE_HEALTH
            ),
            Article(
                id = 100526,
                title = context.resources.getString(R.string._8_Early_Pregnancy_Symptoms_That_Feel_A_Lot_Like_PMS),
                smallTitle = context.resources.getString(R.string.Pregnancy_Or_PMS_8_symptoms),
                content = context.resources.getString(R.string._8_Early_Pregnancy_Symptoms_That_Feel_A_Lot_Like_PMS_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_8_early_pregnancy_symtoms),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_8_early_pregnancy_symtoms),
                parentType = REPRODUCTIVE_HEALTH
            ),
            Article(
                id = 100527,
                title = context.resources.getString(R.string._5_Late_Period_Remedies_Fact_Checked),
                smallTitle = context.resources.getString(R.string.Do_Late_Period_Remedies_Work_),
                content = context.resources.getString(R.string._5_Late_Period_Remedies_Fact_Checked_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_do_late_period_remedies),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_do_late_period_remedies_small),
                parentType = REPRODUCTIVE_HEALTH
            ),
            /**How ovulation affects you*/
            Article(
                id = 100528,
                title = context.resources.getString(R.string.How_Ovulation_Affects_You),
                content = context.resources.getString(R.string.How_Ovulation_Affects_You_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_ovulation_affects_you),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_ovulation_affects_you),
                type = HOW_OVULATION_AFFECTS,
                parentType = REPRODUCTIVE_HEALTH
            ),
            Article(
                id = 100529,
                title = context.resources.getString(R.string.Does_Ovulation_Always_Happen_on_the_Same_Day_of_the_Cycle_),
                content = context.resources.getString(R.string.Does_Ovulation_Always_Happen_on_the_Same_Day_of_the_Cycle_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_does_ovulation_alway_happen),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_does_ovulation_alway_happen),
                parentType = HOW_OVULATION_AFFECTS
            ),
            Article(
                id = 100530,
                title = context.resources.getString(R.string.Why_Are_There_So_Many_Fertile_Days_),
                content = context.resources.getString(R.string.Why_Are_There_So_Many_Fertile_Days_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_why_are_there_so_many_fertile_days),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_why_are_there_so_many_fertile_days),
                parentType = HOW_OVULATION_AFFECTS
            ),
            Article(
                id = 100531,
                title = context.resources.getString(R.string.Pain_During_Ovulation_Can_Be_Normal),
                titleColor = WHITE,
                content = context.resources.getString(R.string.Pain_During_Ovulation_Can_Be_Normal_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_pain_during_ovulation),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_pain_during_ovulation),
                parentType = HOW_OVULATION_AFFECTS
            ),
            Article(
                id = 100532,
                title = context.resources.getString(R.string.How_PMS_and_Ovulation_Affect_Your_Sleep),
                content = context.resources.getString(R.string.How_PMS_and_Ovulation_Affect_Your_Sleep_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_pms_and_ovulation_affect_your_sleep),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_pms_and_ovulation_affect_your_sleep),
                parentType = HOW_OVULATION_AFFECTS
            ),
            Article(
                id = 100533,
                title = context.resources.getString(R.string.Not_Pregnant_10_Other_Causes_of_Late_Periods),
                smallTitle = context.resources.getString(R.string.Late_Period),
                content = context.resources.getString(R.string.Not_Pregnant_10_Other_Causes_of_Late_Periods_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_not_pregnant),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_not_pregnant_small),
                parentType = REPRODUCTIVE_HEALTH
            )
        )
        return articles
    }

    private fun getSexArticles(): List<Article> {
        val articles = listOf(
            /**sex and your cycle*/
            Article(
                id = 100534,
                title = context.resources.getString(R.string.Sex_and_Your_Cycle),
                content = context.resources.getString(R.string.Sex_and_Your_Cycle_content),
                type = SEX_AND_YOUR_CYCLE,
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_sex_and_your_cycle),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_sex_and_your_cycle),
                //                bigImage = Uri.parse("https://cdn1.flamp.ru/06fce15cc8b283dfe7df8b02d7115a72.png"),
                //                smallImage = Uri.parse("https://cdn1.flamp.ru/06fce15cc8b283dfe7df8b02d7115a72.png"),
                parentType = SEX
            ),
            Article(
                id = 100535,
                title = context.resources.getString(R.string.Menstruation_Love_Hate),
                content = context.resources.getString(R.string.Menstruation_Love_Hate_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_menstruation_love_hate),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_menstruation_love_hate),
                parentType = SEX_AND_YOUR_CYCLE
            ),
            Article(
                id = 100536,
                title = context.resources.getString(R.string.Follicular_Phase_Feeling_Friskier_),
                content = context.resources.getString(R.string.Follicular_Phase_Feeling_Friskier_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_follicular_phase_feeling_friskier),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_follicular_phase_feeling_friskier),
                parentType = SEX_AND_YOUR_CYCLE
            ),
            Article(
                id = 100537,
                title = context.resources.getString(R.string.Ovulation_Pleasure_Peaks),
                content = context.resources.getString(R.string.Ovulation_Pleasure_Peaks_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_ovulation_pleasure_peacks),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_ovulation_pleasure_peacks),
                parentType = SEX_AND_YOUR_CYCLE
            ),
            Article(
                id = 100538,
                title = context.resources.getString(R.string.Luteal_Phase_Cooling_Off),
                content = context.resources.getString(R.string.Luteal_Phase_Cooling_Off_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_luteal_phase),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_luteal_phase),
                parentType = SEX_AND_YOUR_CYCLE
            ),
            Article(
                id = 100539,
                title = context.resources.getString(R.string.Learn_how_to_masturbate_really_well_with_these_masturbation_tips),
                smallTitle = context.resources.getString(R.string._9_Life_changing_Masturbation_Tips),
                content = context.resources.getString(R.string.Learn_how_to_masturbate_really_well_with_these_masturbation_tips_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_learn_to_masturbate),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_learn_to_masturbate),
                parentType = SEX
            ),
            Article(
                id = 100540,
                title = context.resources.getString(R.string.Pregnancy_Paranoia_How_to_Handle_It),
                smallTitle = context.resources.getString(R.string.Coping_With_Pregnancy_Paranoia),
                content = context.resources.getString(R.string.Pregnancy_Paranoia_How_to_Handle_It_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_pregn_paranoia),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_pregn_paranoia_small),
                parentType = SEX
            ),
            Article(
                id = 100541,
                title = context.resources.getString(R.string.Is_Anal_Sex_Safe_Key_Rules_to_Follow),
                smallTitle = context.resources.getString(R.string.But_Play_How_to_Safely),
                content = context.resources.getString(R.string.Is_Anal_Sex_Safe_Key_Rules_to_Follow_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_is_anal_sex_safe),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_is_anal_sex_safe),
                parentType = SEX
            ),
            /**how to make sex painless*/
            Article(
                id = 100542,
                title = context.resources.getString(R.string.How_to_Make_Sex_Painless),
                content = context.resources.getString(R.string.How_to_Make_Sex_Painless_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_to_make_sex_painless),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_to_make_sex_painless),
                type = HOW_TO_MAKE_SEX_PAINLESS,
                parentType = SEX
            ),
            Article(
                id = 100543,
                title = context.resources.getString(R.string.Common_Causes_of_Pain_During_Sex),
                content = context.resources.getString(R.string.Common_Causes_of_Pain_During_Sex_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_common_causes_of_pain_during_sex),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_common_causes_of_pain_during_sex),
                parentType = HOW_TO_MAKE_SEX_PAINLESS
            ),
            Article(
                id = 100544,
                title = context.resources.getString(R.string.What_Causes_Vaginismus_and_How_It_Can_Be_Treated),
                content = context.resources.getString(R.string.What_Causes_Vaginismus_and_How_It_Can_Be_Treated_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_what_causes_vaginismus),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_what_causes_vaginismus),
                parentType = HOW_TO_MAKE_SEX_PAINLESS
            ),
            Article(
                id = 100545,
                title = context.resources.getString(R.string.Can_You_Have_Sex_on_Your_Period_Pros_and_Cons_of_Period_Sex),
                content = context.resources.getString(R.string.Can_You_Have_Sex_on_Your_Period_Pros_and_Cons_of_Period_Sex_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_can_you_have_sex_on_your_period),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_can_you_have_sex_on_your_period),
                parentType = HOW_TO_MAKE_SEX_PAINLESS
            ),
            Article(
                id = 100546,
                title = context.resources.getString(R.string.Some_People_Are_Allergic_to_Sperm),
                content = context.resources.getString(R.string.Some_People_Are_Allergic_to_Sperm_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_some_people_allergic),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_some_people_allergic),
                parentType = HOW_TO_MAKE_SEX_PAINLESS
            ),
            Article(
                id = 100547,
                title = context.resources.getString(R.string.Can_You_Get_Herpes_from_Kissing_Someone_Without_an_Outbreak_),
                content = context.resources.getString(R.string.Can_You_Get_Herpes_from_Kissing_Someone_Without_an_Outbreak_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_can_you_get_herpes),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_can_you_get_herpes),
                parentType = HOW_TO_MAKE_SEX_PAINLESS
            ),
            Article(
                id = 100548,
                title = context.resources.getString(R.string.Can_You_Have_Sex_When_You_Have_a_Yeast_Infection),
                titleColor = WHITE,
                content = context.resources.getString(R.string.Can_You_Have_Sex_When_You_Have_a_Yeast_Infection_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_yeast_infection),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_yeast_infection),
                parentType = HOW_TO_MAKE_SEX_PAINLESS
            ),
            Article(
                id = 100549,
                title = context.resources.getString(R.string.Why_Might_Your_Vagina_Stay_Dry_Even_If_You_Are_Turne_On_),
                content = context.resources.getString(R.string.Why_Might_Your_Vagina_Stay_Dry_Even_If_You_Are_Turne_On_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_ovulation_affects_you),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_ovulation_affects_you),
                parentType = HOW_TO_MAKE_SEX_PAINLESS
            ),
            Article(
                id = 100550,
                title = context.resources.getString(R.string.Is_Anal_Sex_Safe_Key_Rules_to_Follow),
                titleColor = WHITE,
                content = context.resources.getString(R.string.Is_Anal_Sex_Safe_Key_Rules_to_Follow_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_is_anal_sex_safe),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_is_anal_sex_safe),
                parentType = HOW_TO_MAKE_SEX_PAINLESS
            ),
            Article(
                id = 100551,
                title = context.resources.getString(R.string.Why_Does_the_Vagina_Sometimes_Make_Sounds_During_Sex_),
                content = context.resources.getString(R.string.Why_Does_the_Vagina_Sometimes_Make_Sounds_During_Sex_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_vagina_sounds),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_vagina_sounds),
                parentType = HOW_TO_MAKE_SEX_PAINLESS
            ),
            Article(
                id = 100552,
                title = context.resources.getString(R.string.Can_Sexual_Abstinence_Affect_Your_Health_),
                titleColor = WHITE,
                content = context.resources.getString(R.string.Can_Sexual_Abstinence_Affect_Your_Health_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_sexual_abstinence),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_sexual_abstinence),
                parentType = HOW_TO_MAKE_SEX_PAINLESS
            ),
            Article(
                id = 100553,
                title = context.resources.getString(R.string.Why_Can_Cystitis_Get_Worse_After_Having_Sex_),
                content = context.resources.getString(R.string.Why_Can_Cystitis_Get_Worse_After_Having_Sex_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_cystitis_get_worse),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_cystitis_get_worse),
                parentType = HOW_TO_MAKE_SEX_PAINLESS
            ),
            Article(
                id = 100554,
                title = context.resources.getString(R.string.Feeling_Down_After_Sex_Can_Be_Normal),
                content = context.resources.getString(R.string.Feeling_Down_After_Sex_Can_Be_Normal_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_feeling_down_after_sex),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_feeling_down_after_sex),
                parentType = HOW_TO_MAKE_SEX_PAINLESS
            ),
            Article(
                id = 100555,
                title = context.resources.getString(R.string.What_Can_Cause_Spotting_After_Sex_),
                titleColor = WHITE,
                content = context.resources.getString(R.string.What_Can_Cause_Spotting_After_Sex_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_spotting_after_sex),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_spotting_after_sex),
                parentType = HOW_TO_MAKE_SEX_PAINLESS
            ),
            /**how to get more pleasure*/
            Article(
                id = 100556,
                title = context.resources.getString(R.string.How_to_Get_More_Pleasure),
                content = context.resources.getString(R.string.How_to_Get_More_Pleasure_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_to_get_more_pleasure),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_to_get_more_pleasure),
                type = HOW_TO_GET_MORE_PLEASURE,
                parentType = SEX
            ),
            Article(
                id = 100557,
                title = context.resources.getString(R.string.Tips_for_Having_Multiple_Orgasms),
                content = context.resources.getString(R.string.Tips_for_Having_Multiple_Orgasms_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_tips_for_having_multiple),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_tips_for_having_multiple),
                parentType = HOW_TO_GET_MORE_PLEASURE
            ),
            Article(
                id = 100558,
                title = context.resources.getString(R.string.Can_You_Orgasm_in_Your_Sleep_),
                content = context.resources.getString(R.string.Can_You_Orgasm_in_Your_Sleep_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_can_you_orgasm_in_your_sleep),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_can_you_orgasm_in_your_sleep),
                parentType = HOW_TO_GET_MORE_PLEASURE
            ),
            Article(
                id = 100559,
                title = context.resources.getString(R.string.Non_Genital_Orgasm_Fact_or_Fiction_),
                titleColor = WHITE,
                content = context.resources.getString(R.string.Non_Genital_Orgasm_Fact_or_Fiction_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_non_genital_orgasm),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_non_genital_orgasm),
                parentType = HOW_TO_GET_MORE_PLEASURE
            ),
            Article(
                id = 100560,
                title = context.resources.getString(R.string.Sex_in_Unusual_Places_Doing_It_Right),
                content = context.resources.getString(R.string.Sex_in_Unusual_Places_Doing_It_Right_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_sex_in_unusual_places),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_sex_in_unusual_places),
                parentType = HOW_TO_GET_MORE_PLEASURE
            ),
            Article(
                id = 100561,
                title = context.resources.getString(R.string.Can_You_Have_a_Wet_Dream_If_You_Don_t_Orgasm_When_You_re_Awake_),
                content = context.resources.getString(R.string.Can_You_Have_a_Wet_Dream_If_You_Don_t_Orgasm_When_You_re_Awake_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_can_i_have_wet_dream),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_can_i_have_wet_dream),
                parentType = HOW_TO_GET_MORE_PLEASURE
            ),
            Article(
                id = 100562,
                title = context.resources.getString(R.string.What_Can_Cause_an_Inability_to_Orgasm_),
                content = context.resources.getString(R.string.What_Can_Cause_an_Inability_to_Orgasm_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_what_can_cause_inability_to_orgasm),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_what_can_cause_inability_to_orgasm),
                parentType = HOW_TO_GET_MORE_PLEASURE
            ),
            Article(
                id = 100563,
                title = context.resources.getString(R.string.How_Does_Masturbation_Affect_Health_),
                titleColor = WHITE,
                content = context.resources.getString(R.string.How_Does_Masturbation_Affect_Health_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_does_masturbation_affect),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_does_masturbation_affect),
                parentType = HOW_TO_GET_MORE_PLEASURE
            ),
            Article(
                id = 100564,
                title = context.resources.getString(R.string.What_Does_Sex_Positive_Mean),
                content = context.resources.getString(R.string.What_Does_Sex_Positive_Mean_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_sex_positive),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_sex_positive),
                parentType = HOW_TO_GET_MORE_PLEASURE
            ),
            Article(
                id = 100565,
                title = context.resources.getString(R.string.Is_It_Safe_for_You_to_Take_Viagra_),
                content = context.resources.getString(R.string.Is_It_Safe_for_You_to_Take_Viagra_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_viagra),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_viagra),
                parentType = HOW_TO_GET_MORE_PLEASURE
            ),
            Article(
                id = 100566,
                title = context.resources.getString(R.string._7_Period_Sex_FAQs),
                content = context.resources.getString(R.string._7_Period_Sex_FAQs_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_7_period_sex_faq),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_7_period_sex_faq),
                parentType = SEX
            ),
        )
        return articles
    }

    private fun getYourCyclePhase(): List<Article> {
        val articles = listOf(
            Article(
                id = 100567,
                title = context.resources.getString(R.string.Hate_the_Wait_What_Are_the_Earliest_Signs_of_Pregnancy),
                smallTitle = context.resources.getString(R.string.Could_You_Be_Pregnant_),
                content = context.resources.getString(R.string.Hate_the_Wait_What_Are_the_Earliest_Signs_of_Pregnancy_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_hate_the_wait),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_hate_the_wait_small),
                parentType = YOUR_CYCLE_PHASE
            ),
            Article(
                id = 100568,
                title = context.resources.getString(R.string.Your_PMS_Action_Plan),
                smallTitle = context.resources.getString(R.string.PMS_Action_Plan),
                content = context.resources.getString(R.string.Your_PMS_Action_Plan_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_your_pms_plan),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_your_pms_plan),
                parentType = YOUR_CYCLE_PHASE
            ),
            Article(
                id = 100569,
                title = context.resources.getString(R.string._4_Ways_to_Ease_Hormonal_Bloating),
                content = context.resources.getString(R.string._4_Ways_to_Ease_Hormonal_Bloating_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_4_ways_ease_hormonal),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_4_ways_ease_hormonal_small),
                parentType = YOUR_CYCLE_PHASE
            ),
            Article(
                id = 100570,
                title = context.resources.getString(R.string.Luteal_Phase_Cooling_Off),
                smallTitle = context.resources.getString(R.string.Navigating_Sex_During_PMS),
                content = context.resources.getString(R.string.Luteal_Phase_Cooling_Off_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_luteal_phase),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_luteal_phase_small),
                parentType = YOUR_CYCLE_PHASE
            ),
        )
        return articles
    }

    private fun getTheLatest(): List<Article> {
        val articles = listOf(
            Article(
                id = 100571,
                title = context.resources.getString(R.string._7_myths_that_stop_us_from_enjoying_masturbation),
                smallTitle = context.resources.getString(R.string.Masturbation_Myths_to_Unlearn),
                content = context.resources.getString(R.string._7_myths_that_stop_us_from_enjoying_masturbation_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_masturbation_myths),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_masturbation_myths),
                parentType = THE_LATEST
            ),
            Article(
                id = 100572,
                title = context.resources.getString(R.string.The_inside_scoop_on_period_poop),
                smallTitle = context.resources.getString(R.string.Period_Poop_Talk),

                content = context.resources.getString(R.string.The_inside_scoop_on_period_poop_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_period_poop_talk),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_period_poop_talk),
                parentType = THE_LATEST
            ),
            Article(
                id = 100573,
                title = context.resources.getString(R.string._7_Reasons_t_Love_Your_Discharge_Because_It_Is_Magical),
                smallTitle = context.resources.getString(R.string.Reasons_to_Love_Your_Discharge),
                content = context.resources.getString(R.string._7_Reasons_t_Love_Your_Discharge_Because_It_Is_Magical_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_7_reasons_to_love_your_dischange),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_7_reasons_to_love_your_dischange_small),
                parentType = THE_LATEST
            ),
            Article(
                id = 100574,
                title = context.resources.getString(R.string._5_Things_You_Need_in_Your_Sex_Toy_Starter_Kit),
                smallTitle = context.resources.getString(R.string.Your_Sex_Toy_Starter_Kit),
                content = context.resources.getString(R.string._5_Things_You_Need_in_Your_Sex_Toy_Starter_Kit_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_your_sex_toy_starter),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_your_sex_toy_starter),
                parentType = THE_LATEST
            ),
        )
        return articles
    }

    private fun getLGBTQ(): List<Article> {
        val articles = listOf(
            Article(
                id = 100575,
                title = context.resources.getString(R.string.How_to_Navigate_Your_Period_When_You_re_Trans_or_Non_Binary),
                smallTitle = context.resources.getString(R.string.Period_Advice_for_Trans_People),
                content = context.resources.getString(R.string.How_to_Navigate_Your_Period_When_You_re_Trans_or_Non_Binary_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_period_advice_for_trans),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_period_advice_for_trans),
                parentType = LGBTQ
            ),
            Article(
                id = 100576,
                title = context.resources.getString(R.string.What_LGBTQ_People_Need_to_Know_About_Birth_Control),
                smallTitle = context.resources.getString(R.string.An_LGBTQ_Guide_to_Birth_Comtrol),
                content = context.resources.getString(R.string.What_LGBTQ_People_Need_to_Know_About_Birth_Control_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_lgbtq_guide_to_birth),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_lgbtq_guide_to_birth),
                parentType = LGBTQ
            ),
            Article(
                id = 100577,
                title = context.resources.getString(R.string.How_to_Have_Safer_Sex_If_You_re_LGBTQ_),
                smallTitle = context.resources.getString(R.string.How_to_Have_Safer_LGBTQ_Sex),
                content = context.resources.getString(R.string.How_to_Have_Safer_Sex_If_You_re_LGBTQ_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_to_have_safer_lgbtq),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_to_have_safer_lgbtq_small),
                parentType = LGBTQ
            ),
            Article(
                id = 100578,
                title = context.resources.getString(R.string.From_Sperm_Donors_to_IVF_Becoming_an_LGBTQ_Parent),
                
                content = context.resources.getString(R.string.From_Sperm_Donors_to_IVF_Becoming_an_LGBTQ_Parent_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_to_have_become_lgbtq),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_to_have_become_lgbtq),
                parentType = LGBTQ
            ),
        )
        return articles
    }

    private fun getTipsForPainFreeCycle(): List<Article> {
        val articles = listOf(
            /**12 ways to deal with cramps*/
            Article(
                id = 100579,
                title = context.resources.getString(R.string._12_Ways_to_Deal_with_Cramps),
                
                content = context.resources.getString(R.string._12_Ways_to_Deal_with_Cramps_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_12_ways_to_deal_with_cramps),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_12_ways_to_deal_with_cramps_small),
                type = WAYS_TO_DEAL_WITH_CRAMPS,
                parentType = TIPS_FOR_FREE_PAIN
            ),
            Article(
                id = 100580,
                title = context.resources.getString(R.string.Heat_and_Cold_on_Your_Belly_Do_s_and_Don_ts),
                
                content = context.resources.getString(R.string.Heat_and_Cold_on_Your_Belly_Do_s_and_Don_ts_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_heat_and_cold_on_belly),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_heat_and_cold_on_belly),
                parentType = WAYS_TO_DEAL_WITH_CRAMPS
            ),
            Article(
                id = 100581,
                title = context.resources.getString(R.string.Can_Ginger_Relieve_Period_Pain_),
                
                content = context.resources.getString(R.string.Can_Ginger_Relieve_Period_Pain_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_can_ginger_relive_period_pain),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_can_ginger_relive_period_pain),
                parentType = WAYS_TO_DEAL_WITH_CRAMPS
            ),
            Article(
                id = 100582,
                title = context.resources.getString(R.string.Self_Massage_for_Immediate_Pain_Relief),
                titleColor=WHITE,
                content = context.resources.getString(R.string.Self_Massage_for_Immediate_Pain_Relief_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_self_massage),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_self_massage),
                parentType = WAYS_TO_DEAL_WITH_CRAMPS
            ),
            Article(
                id = 100583,
                title = context.resources.getString(R.string.Kinesio_Tape_for_Pain_Relief),
                
                content = context.resources.getString(R.string.Kinesio_Tape_for_Pain_Relief_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_kinesio_tape_for_pain_relief),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_kinesio_tape_for_pain_relief),
                parentType = WAYS_TO_DEAL_WITH_CRAMPS
            ),
            Article(
                id = 100584,
                title = context.resources.getString(R.string.Acupressure_to_Relieve_Cramps),
                
                content = context.resources.getString(R.string.Acupressure_to_Relieve_Cramps_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_acupressure_to_relieve),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_acupressure_to_relieve),
                parentType = WAYS_TO_DEAL_WITH_CRAMPS
            ),
            Article(
                id = 100585,
                title = context.resources.getString(R.string.Herbs_as_Natural_Pain_Remedy),
                
                content = context.resources.getString(R.string.Herbs_as_Natural_Pain_Remedy_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_herbs_as_natural_pain_remedy),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_herbs_as_natural_pain_remedy),
                parentType = WAYS_TO_DEAL_WITH_CRAMPS
            ),
            Article(
                id = 100586,
                title = context.resources.getString(R.string.Can_an_Orgasm_Make_Cramps_Disappear),
                
                content = context.resources.getString(R.string.Can_an_Orgasm_Make_Cramps_Disappear_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_can_orgasm_make_cramps_dissapear),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_can_orgasm_make_cramps_dissapear),
                parentType = WAYS_TO_DEAL_WITH_CRAMPS
            ),
            Article(
                id = 100587,
                title = context.resources.getString(R.string.Cramps_Keeping_You_Up_Try_Switching_Up_Your_Sleeping_Position),
                
                content = context.resources.getString(R.string.Cramps_Keeping_You_Up_Try_Switching_Up_Your_Sleeping_Position_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_cramps_keeping_you_up),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_cramps_keeping_you_up),
                parentType = WAYS_TO_DEAL_WITH_CRAMPS
            ),
            Article(
                id = 100588,
                title = context.resources.getString(R.string.Will_Relaxation_Help_If_Your_Belly_Hurts),
                
                content = context.resources.getString(R.string.Will_Relaxation_Help_If_Your_Belly_Hurts_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_will_relaxation_help_if_your_belly),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_will_relaxation_help_if_your_belly_small),
                parentType = WAYS_TO_DEAL_WITH_CRAMPS
            ),
            Article(
                id = 100589,
                title = context.resources.getString(R.string.Your_Diet_Can_Affect_Your_Cramps),
                
                content = context.resources.getString(R.string.Your_Diet_Can_Affect_Your_Cramps_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_your_diet_can_affect_your_cramps),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_your_diet_can_affect_your_cramps),
                parentType = WAYS_TO_DEAL_WITH_CRAMPS
            ),
            Article(
                id = 100590,
                title = context.resources.getString(R.string.Use_Light_Exercise_to_Stay_Pain_Free),
                titleColor= WHITE,
                
                content = context.resources.getString(R.string.Use_Light_Exercise_to_Stay_Pain_Free_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_use_light_exercise_to_stay_pain_free),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_use_light_exercise_to_stay_pain_free),
                parentType = WAYS_TO_DEAL_WITH_CRAMPS
            ),
            Article(
                id = 100591,
                title = context.resources.getString(R.string.Medications_for_Period_Pain),
                
                content = context.resources.getString(R.string.Medications_for_Period_Pain_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_medications_for_period_pain),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_medications_for_period_pain),
                parentType = WAYS_TO_DEAL_WITH_CRAMPS
            ),
            Article(
                id = 100592,
                title = context.resources.getString(R.string.Why_Your_Breasts_Are_Sore_and_What_You_Can_Do),
                smallTitle = context.resources.getString(R.string.Sore_Boods_Club_What_You_Can_Do_),
                content = context.resources.getString(R.string.Why_Your_Breasts_Are_Sore_and_What_You_Can_Do_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_why_your_breast_are_sore),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_why_your_breast_are_sore),
                parentType = TIPS_FOR_FREE_PAIN
            ),
            Article(
                id = 100593,
                title = context.resources.getString(R.string.Tips_for_Tackling_Period_Headaches_and_Migraines),
                smallTitle = context.resources.getString(R.string.Period_Headaches_Be_Gone),

                content = context.resources.getString(R.string.Tips_for_Tackling_Period_Headaches_and_Migraines_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_tips_for_tackling_period),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_tips_for_tackling_period),
                parentType = TIPS_FOR_FREE_PAIN
            ),
            Article(
                id = 100594,
                title = context.resources.getString(R.string.Always_Tired_It_Could_Be_Period_Fatigue),
                smallTitle = context.resources.getString(R.string.Fatigue_and_Your_Cycle),
                content = context.resources.getString(R.string.Always_Tired_It_Could_Be_Period_Fatigue_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_always_tired_it_could_be_period_fatique),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_always_tired_it_could_be_period_fatique),
                parentType = TIPS_FOR_FREE_PAIN
            ),
            Article(
                id = 100595,
                title = context.resources.getString(R.string.Backache_The_Less_Talked_About_Period_Pain),
                smallTitle = context.resources.getString(R.string.Cramps_in_Your_Back_),
                content = context.resources.getString(R.string.Backache_The_Less_Talked_About_Period_Pain_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_backage_the_less_talked_about_period_pain),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_backage_the_less_talked_about_period_pain),
                parentType = TIPS_FOR_FREE_PAIN
            ),
            /**all things mood swings*/
            Article(
                id = 100596,
                title = context.resources.getString(R.string.All_Things_Mood_Swings),
                
                content = context.resources.getString(R.string.All_Things_Mood_Swings_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_all_things_mood_swings),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_all_things_mood_swings_small),
                type = ALL_THINGS_MOOD_SWINGS,
                parentType = TIPS_FOR_FREE_PAIN
            ),
            Article(
                id = 100597,
                title = context.resources.getString(R.string.Mood_Swings_and_Your_Cycle),
                
                content = context.resources.getString(R.string.Mood_Swings_and_Your_Cycle_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_mood_swings_and_your_cycle),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_mood_swings_and_your_cycle),
                parentType = ALL_THINGS_MOOD_SWINGS
            ),
            Article(
                id = 100598,
                title = context.resources.getString(R.string._7_Causes_of_Mood_Swings_That_Aren_t_PMS),
                
                content = context.resources.getString(R.string._7_Causes_of_Mood_Swings_That_Aren_t_PMS_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_7_causes_of_mood_swings),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_7_causes_of_mood_swings_small),
                parentType = ALL_THINGS_MOOD_SWINGS
            ),
            Article(
                id = 100598,
                title = context.resources.getString(R.string._7_Causes_of_Mood_Swings_That_Aren_t_PMS),
                
                content = context.resources.getString(R.string._7_Causes_of_Mood_Swings_That_Aren_t_PMS_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_7_causes_of_mood_swings),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_7_causes_of_mood_swings),
                parentType = ALL_THINGS_MOOD_SWINGS
            ),
            Article(
                id = 100599,
                title = context.resources.getString(R.string.Birth_Control_and_Mood_Swings_Cause_or_Cure),
                
                content = context.resources.getString(R.string.Birth_Control_and_Mood_Swings_Cause_or_Cure_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_birth_control_and_mood_swings),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_birth_control_and_mood_swings),
                parentType = ALL_THINGS_MOOD_SWINGS
            ),
            Article(
                id = 100600,
                title = context.resources.getString(R.string.Real_People_Share_How_To_Beat_The_Blues),
                
                content = context.resources.getString(R.string.Real_People_Share_How_To_Beat_The_Blues_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_real_people_share_how_to_beat),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_real_people_share_how_to_beat),
                parentType = ALL_THINGS_MOOD_SWINGS
            ),
            Article(
                id = 100601,
                title = context.resources.getString(R.string.How_Exercise_Helped_Me_Beat_Mood_Swings),
                
                content = context.resources.getString(R.string.How_Exercise_Helped_Me_Beat_Mood_Swings_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_exercise_helped_me_beat_mood),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_exercise_helped_me_beat_mood),
                parentType = ALL_THINGS_MOOD_SWINGS
            ),
            /**beat the bloat*/
            Article(
                id = 100602,
                title = context.resources.getString(R.string.Beat_the_Bloat),
                
                content = context.resources.getString(R.string.Beat_the_Bloat_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_beat_the_bloat),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_beat_the_bloat),
                type = BEAT_THE_BLOAT,
                parentType = TIPS_FOR_FREE_PAIN
            ),
            Article(
                id = 100603,
                title = context.resources.getString(R.string.Why_You_re_Bloated_Cycle_or_Gut),
                
                content = context.resources.getString(R.string.Why_You_re_Bloated_Cycle_or_Gut_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_why_you_arent_bloated),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_why_you_arent_bloated),
                parentType = BEAT_THE_BLOAT
            ),
            Article(
                id = 100604,
                title = context.resources.getString(R.string._4_Ways_to_Ease_Hormonal_Bloating),
                
                content = context.resources.getString(R.string._4_Ways_to_Ease_Hormonal_Bloating_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_4_ways_ease_hormonal),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_4_ways_ease_hormonal_small),
                parentType = BEAT_THE_BLOAT
            ),
            Article(
                id = 100605,
                title = context.resources.getString(R.string._6_Signs_Your_Bloating_Isn_t_PMS),
                
                content = context.resources.getString(R.string._6_Signs_Your_Bloating_Isn_t_PMS_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_6_signs_your_bloating),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_6_signs_your_bloating),
                parentType = BEAT_THE_BLOAT
            ),
            /**tips to relieve pms symptoms*/
            Article(
                id = 100606,
                title = context.resources.getString(R.string.Tips_to_Relieve_PMS_Sympton),
                
                content = context.resources.getString(R.string.Tips_to_Relieve_PMS_Sympton),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_tips_to_relieve_pms_symptom),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_tips_to_relieve_pms_symptom_small),
                type = TIPS_TO_RELIEVE_PMS_SYMPTOM,
                parentType = TIPS_FOR_FREE_PAIN
            ),
            Article(
                id = 100607,
                title = context.resources.getString(R.string.Premenstrual_Syndrome_Myth_or_Reality_),
                
                content = context.resources.getString(R.string.Premenstrual_Syndrome_Myth_or_Reality_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_premenstrual_syndrome),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_premenstrual_syndrome),
                parentType = TIPS_TO_RELIEVE_PMS_SYMPTOM
            ),
            Article(
                id = 100608,
                title = context.resources.getString(R.string.PMS_Has_100_Symptoms_Which_of_Them_Do_You_Experience_),
                titleColor = WHITE,
                content = context.resources.getString(R.string.PMS_Has_100_Symptoms_Which_of_Them_Do_You_Experience__content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_pms_has_100_symptoms),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_pms_has_100_symptoms),
                parentType = TIPS_TO_RELIEVE_PMS_SYMPTOM
            ),
            Article(
                id = 100609,
                title = context.resources.getString(R.string.Heat_and_Cold_on_Your_Belly_Do_s_and_Don_ts),
                
                content = context.resources.getString(R.string.Heat_and_Cold_on_Your_Belly_Do_s_and_Don_ts_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_heat_and_cold_on_belly),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_heat_and_cold_on_belly),
                parentType = TIPS_TO_RELIEVE_PMS_SYMPTOM
            ),
            Article(
                id = 100610,
                title = context.resources.getString(R.string.Your_Diet_Can_Affect_Your_Cramps),
                
                content = context.resources.getString(R.string.Your_Diet_Can_Affect_Your_Cramps_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_your_diet_can_affect_your_cramps),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_your_diet_can_affect_your_cramps),
                parentType = TIPS_TO_RELIEVE_PMS_SYMPTOM
            ),
            Article(
                id = 100611,
                title = context.resources.getString(R.string.Will_Relaxation_Help_If_Your_Belly_Hurts),
                
                content = context.resources.getString(R.string.Will_Relaxation_Help_If_Your_Belly_Hurts_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_will_relaxation_help_if_your_belly),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_will_relaxation_help_if_your_belly),
                parentType = TIPS_TO_RELIEVE_PMS_SYMPTOM
            ),
            Article(
                id = 100612,
                title = context.resources.getString(R.string.Foods_to_Avoid_If_You_Want_to_Prevent_Bloating_Before_Your_Period),
                
                content = context.resources.getString(R.string.Foods_to_Avoid_If_You_Want_to_Prevent_Bloating_Before_Your_Period_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_foods_to_avoid_if_you_want_to_prevent),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_foods_to_avoid_if_you_want_to_prevent),
                parentType = TIPS_TO_RELIEVE_PMS_SYMPTOM
            ),
            Article(
                id = 100613,
                title = context.resources.getString(R.string.You_May_Have_Headaches_Before_Menstruation),
                
                content = context.resources.getString(R.string.You_May_Have_Headaches_Before_Menstruation_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_you_may_have_headaches_before_menstruation),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_you_may_have_headaches_before_menstruation),
                parentType = TIPS_TO_RELIEVE_PMS_SYMPTOM
            ),
            Article(
                id = 100614,
                title = context.resources.getString(R.string.Hot_Flashes_and_Sweating_Before_Menstruation),
                titleColor = WHITE,
                content = context.resources.getString(R.string.Hot_Flashes_and_Sweating_Before_Menstruation_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_hot_flashes_and_sweating_before_menstruation),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_hot_flashes_and_sweating_before_menstruation),
                parentType = TIPS_TO_RELIEVE_PMS_SYMPTOM
            ),
            Article(
                id = 100615,
                title = context.resources.getString(R.string.An_Increased_Appetite_Before_and_During_Your_Period),
                
                content = context.resources.getString(R.string.An_Increased_Appetite_Before_and_During_Your_Period_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_increased_appetite_before_and_during),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_increased_appetite_before_and_during),
                parentType = TIPS_TO_RELIEVE_PMS_SYMPTOM
            ),
            Article(
                id = 100616,
                title = context.resources.getString(R.string.Potassium_Can_Relieve_PMS_Symptoms),
                
                content = context.resources.getString(R.string.Potassium_Can_Relieve_PMS_Symptoms_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_potassimus_can_relieve),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_potassium_small),
                parentType = TIPS_TO_RELIEVE_PMS_SYMPTOM
            ),
            Article(
                id = 100617,
                title = context.resources.getString(R.string.Acupressure_to_Relieve_Cramps),
                
                content = context.resources.getString(R.string.Acupressure_to_Relieve_Cramps_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_acupressure_to_relieve),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_acupressure_to_relieve),
                parentType = TIPS_TO_RELIEVE_PMS_SYMPTOM
            ),
            Article(
                id = 100618,
                title = context.resources.getString(R.string.How_Are_Your_Age_and_PMS_Connected),
                
                content = context.resources.getString(R.string.How_Are_Your_Age_and_PMS_Connected_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_are_your_age_and_pms_connected),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_are_your_age_and_pms_connected),
                parentType = TIPS_TO_RELIEVE_PMS_SYMPTOM
            ),
            Article(
                id = 100619,
                title = context.resources.getString(R.string.What_Can_Worsen_PMS_Symptoms),
                
                content = context.resources.getString(R.string.What_Can_Worsen_PMS_Symptoms_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_what_can_worsen_pms),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_what_can_worsen_pms),
                parentType = TIPS_TO_RELIEVE_PMS_SYMPTOM
            ),
        )
        return articles
    }

    private fun getNutritionAndFitness(): List<Article> {
        val articles = listOf(
            /**12 ways to deal with cramps*/
            Article(
                id = 100620,
                title = context.resources.getString(R.string.Workouts_and_Your_Cycle),
                
                content = context.resources.getString(R.string.Workouts_and_Your_Cycle_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_workouts_and_your_cycle),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_workouts_and_your_cycle_small),
                type = WORKOUTS_AND_YOUR_CYCLE,
                parentType = NUTRITION_AND_FITNESS
            ),
            Article(
                id = 100621,
                title = context.resources.getString(R.string.How_Hormones_Can_Impact_Exercise),
                
                content = context.resources.getString(R.string.How_Hormones_Can_Impact_Exercise_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_hormones_can_impact),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_how_hormones_can_impact),
                parentType = WORKOUTS_AND_YOUR_CYCLE
            ),
            Article(
                id = 100622,
                title = context.resources.getString(R.string.Menstruation_Keep_It_Low_Key),
                
                content = context.resources.getString(R.string.Menstruation_Keep_It_Low_Key_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_menstruation_keep_it_low_key),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_menstruation_keep_it_low_key),
                parentType = WORKOUTS_AND_YOUR_CYCLE
            ),
            Article(
                id = 100623,
                title = context.resources.getString(R.string.Follicular_Phase_Up_the_Intensity),
                
                content = context.resources.getString(R.string.Follicular_Phase_Up_the_Intensity_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_follicular_phase),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_follicular_phase_small),
                parentType = WORKOUTS_AND_YOUR_CYCLE
            ),
            Article(
                id = 100624,
                title = context.resources.getString(R.string.Ovulation_Pull_a_Personal_Best),
                
                content = context.resources.getString(R.string.Ovulation_Pull_a_Personal_Best_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_ovulation_pull_a_personal_best),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_ovulation_pull_a_personal_best),
                parentType = WORKOUTS_AND_YOUR_CYCLE
            ),
            Article(
                id = 100625,
                title = context.resources.getString(R.string.Luteal_Phase_Go_for_Moderate_Exercise),
                
                content = context.resources.getString(R.string.Luteal_Phase_Go_for_Moderate_Exercise_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_luteal_phase_go_for_moderate),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_luteal_phase_go_for_moderate_small),
                parentType = WORKOUTS_AND_YOUR_CYCLE
            ),
            Article(
                id = 100626,
                title = context.resources.getString(R.string.Female_Health_and_Nutrition),
                
                content = context.resources.getString(R.string.Female_Health_and_Nutrition_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_female_health_and_nutrition),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_female_health_and_nutrition),
                type = FEMALE_HEALTH_AND_NUTRITION,
                parentType = NUTRITION_AND_FITNESS
            ),
            Article(
                id = 100627,
                title = context.resources.getString(R.string.An_Increased_Appetite_Before_and_During_Your_Period),
                
                content = context.resources.getString(R.string.An_Increased_Appetite_Before_and_During_Your_Period_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_increased_appetite_before_and_during),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_increased_appetite_before_and_during),
                parentType = FEMALE_HEALTH_AND_NUTRITION
            ),
            Article(
                id = 100628,
                title = context.resources.getString(R.string.Hormones_Can_Affect_Your_Appetite),
                
                content = context.resources.getString(R.string.Hormones_Can_Affect_Your_Appetite_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_hormones_can_affect_your_appetite),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_hormones_can_affect_your_appetite),
                parentType = FEMALE_HEALTH_AND_NUTRITION
            ),
            Article(
                id = 100629,
                title = context.resources.getString(R.string.Managing_Hunger_While_on_Your_Period),
                
                content = context.resources.getString(R.string.Managing_Hunger_While_on_Your_Period_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_managing_hunger_while_on_your_period),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_managing_hunger_while_on_your_period),
                parentType = FEMALE_HEALTH_AND_NUTRITION
            ),
            Article(
                id = 100630,
                title = context.resources.getString(R.string.Potassium_Can_Relieve_PMS_Symptoms),
                
                content = context.resources.getString(R.string.Potassium_Can_Relieve_PMS_Symptoms_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_potassimus_can_relieve),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_potassium_small),
                parentType = FEMALE_HEALTH_AND_NUTRITION
            ),
            Article(
                id = 100631,
                title = context.resources.getString(R.string.Foods_to_Avoid_If_You_Want_to_Prevent_Bloating_Before_Your_Period),
                
                content = context.resources.getString(R.string.Foods_to_Avoid_If_You_Want_to_Prevent_Bloating_Before_Your_Period_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_foods_to_avoid_if_you_want_to_prevent),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_foods_to_avoid_if_you_want_to_prevent),
                parentType = FEMALE_HEALTH_AND_NUTRITION
            ),
            Article(
                id = 100632,
                title = context.resources.getString(R.string.Your_Diet_Can_Affect_Your_Cramps),
                
                content = context.resources.getString(R.string.Your_Diet_Can_Affect_Your_Cramps_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_your_diet_can_affect_your_cramps),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_your_diet_can_affect_your_cramps),
                parentType = FEMALE_HEALTH_AND_NUTRITION
            ),
            Article(
                id = 100633,
                title = context.resources.getString(R.string.Staying_Hydrated_Makes_Your_Cervical_Mucus_Better),
                
                content = context.resources.getString(R.string.Staying_Hydrated_Makes_Your_Cervical_Mucus_Better_content),
                bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_staying_hydrated_makes_your_cervical_muscus_better),
                smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.raw.im_staying_hydrated_makes_your_cervical_muscus_better_small),
                parentType = FEMALE_HEALTH_AND_NUTRITION
            ),
        )
        return articles
    }

    override fun getArticlesFlow(): StateFlow<List<Article>> {
        if (articles.isEmpty()) {
            getArticles()
        }
        val stateFlow = MutableStateFlow(articles.values.toList())
/*
        ioScope.launch {
            Timber.d("new article start adding")
            delay(5000)
            val oldArticles = stateFlow.value.toMutableList()
            oldArticles.add(
                Article(
                    id = IdHelper.getId(),
                    title = "EARLY_SIGNS_OF_PREGNANCY_2",
                    content = context.resources.getString(R.string.Early_Signs_of_Pregnancy_content),
                    type = EARLY_SIGNS_OF_PREGNANCY,
                    */
/*   bigImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.drawable.im_big_placeholder_jpg),
                       smallImage = Uri.parse("android.resource://woman.calendar.every.day.health/" + R.drawable.im_placeholder)
                   )*//*

                    bigImage = Uri.parse("https://cdn1.flamp.ru/06fce15cc8b283dfe7df8b02d7115a72.png"),
                    smallImage = Uri.parse("https://cdn1.flamp.ru/06fce15cc8b283dfe7df8b02d7115a72.png"),
                    parentType = REPRODUCTIVE_HEALTH
                ),
            )
            Timber.d("emited new article")
            articles.putAll(oldArticles.associateBy { it.id })
            stateFlow.emit(articles.values.toList())
        }
*/
        return stateFlow
    }
}