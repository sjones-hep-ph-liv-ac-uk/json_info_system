I decided to make an SRR schema that would pass all the examples  schemas from here:

https://twiki.cern.ch/twiki/bin/view/LCG/StorageSpaceAccounting#SRR_implementation_by_the_storag

Namely: Examples of implementation

    DPM example for Prague
    EOS example for CERN
    STORM example for CNAF
    dCache example for Desy 

So, I had to take out quite a few constraints and then adapt the integrity checker to account for that.

Here is the change list:

For Prague (DPM) id and numberoffiles were allowed to be optional.

For CERN EOS, I made offline and nearline storage capacity entries optional.

I also allowed reservedsize to be optional.

The name in storageshares was made optional, also servingstate and assignedendpoints. qualitylevel was made optional in various places. And the datastores tag was made optional.

For CNAF, STORM, I added custodial and replica to the retentionpolicy enum.

And for DESY dCache, various things were made optional; storagecapacity, and storageshares path, storageshares name.

The DESY dCache example used lastupdated instead of latestupdate, so I had to allow both, but optional. Also, the storageendpoint name, in two sections, was "xrootd endpoint on prometheus.desy.de", i.e. not unique, I had to allow that name to be non-unique.

Due to all these relaxed constraints, there were knock on effects with the integrity checker that I had to fix.

Ste
27th Aug 2019
