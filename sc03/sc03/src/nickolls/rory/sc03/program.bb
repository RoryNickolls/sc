clear X;
incr X;
incr X;
clear Y;
incr Y;
incr Y;
incr Y;
clear Z;
# multiply X and Z
while X not 0 do;
	clear W;
	while Y not 0 do;
		incr Z;
		incr W;
		decr Y;
		end;
	while W not 0 do;
		incr Y;
		decr W;
	end;
	decr X;
end;

print Z;

# count to 2 and print it
def count;
	incr F;
	incr F;
	print F;
end;

# run count function
run count;
