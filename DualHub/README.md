# Project Title
Testing out Dual Hub Configuration

# Dual Hub Connectivity

From: https://www.reddit.com/r/FTC/comments/6twjaj/how_to_connect_two_rev_expansion_hubs_together/
Thanks to Team: 7316

- Plug in your FTC legal phone which has robot controller version 3.1 (NECESSARY) into the one of your expansion hubs via the USB. For sake of simplicity, I will now refer to it as 'hub A'. (DO NOT DO ANYTHING WITH THE OTHER HUB)
- Power up hub A by plugging your 12v battery into female xt30 port.
- On the phone, open FTC RC and open the 'Settings' screen. Go to advanced settings, then click 'change expansion hub address'. Make note of what the current address is, but do not change it.
- Exit settings, go back to the RC main screen.
- Now unplug everything from hub A.
- Now, plug power and your phone into the other hub, which i'll refer to as hub B. 
- On the phone, open FTC RC and open the 'Settings' screen. Go to advanced settings, then click 'change expansion hub address'. Now, change the address to a number that is NOT the address on Hub A. Exit settings, go back to the RC main screen. Now unplug everything from hub B.
- Find the 3pin JST PH cable that came with the purchase of the expansion hub. Plug one end into the RS485 on Hub A and the other end into Hub B's RS485.
- Find the XT30 male to female which came with the hub, and plug the female end into hub A and the male end into hub B. connect battery into the female port on hub A 
- Now, plug your phone into hub A. make an ENTIRELY NEW configuration. the phone should scan and see one hub (hub a), with its name and 'portal'. click on this. that should open you to a new screen, with the names of the two expansion hubs you have daisy chained.

# Naming Convesion 