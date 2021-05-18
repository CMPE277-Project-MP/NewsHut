package sjsu.cmpelkk.myappandroid.myutil

import com.amazonaws.regions.Regions

class DynamoDBHelper {

    companion object{
        const val COGNITO_IDP_ID = "us-east-1:c8c51b90-7d93-49c5-a901-02104f088652"
        val COGNITO_IDP_REGION = Regions.US_EAST_1  //allotted region
        const val TABLE_NAME = "Favorites"    //table holding favorites data
        const val USERS_TABLE="Users" //table holding users
    }

}