The file, storagesummary_lanc.json, is straight from the DPM python script at Lancaster. It would not go through v4.0 of the srr checker.

So, to make it go through, this is what I did. I copied the script output file to storagesummary_lanc_edtowork.json

And I made these chnages by hand to it.

*) The id fields were missing everywhere, so I added lots of those.
*) The numberoffiles fields were frequently missing in the storageshares section. I added those.
*) Two storageshares objects shared the same name, "dteam". I made those distinct.

There were also changes to the schema to make it less strict so it fits the script output. The new schema is v4.1

The changes to the schema were: 
    
*) policyrules have a TODO note against them [9], so I've made them optional.
*) accessmode has a TBD note [11] against it; hence made optional.
*) jitter in servicetype enum, hence made it optional. It needs WLCG agreement.
*) jitter in capabilities enum, hence made it optional. It needs WLCG agreement.
*) Script omits storagecapacity; hence I make it optional based on assumption.
*) Script omits interfaceversion, hence I make it optional based on assumption.

There were also small chnages to the Java integrity checker to make it work with the new schema.

With this, the script output from Lancaster passes the validation.

Ste
12 Aug 2019

